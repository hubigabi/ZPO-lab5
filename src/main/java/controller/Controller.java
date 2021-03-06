package controller;

import annotation.Name;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.TableData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private ComboBox<Class> chooseClass_cb;

    @FXML
    private ComboBox<Object> chooseObject_cb;

    @FXML
    private TableView<TableData> tableView;

    @FXML
    @Name("No.")
    private TableColumn<TableData, Integer> tableColumn1;

    @FXML
    @Name("Field")
    private TableColumn<TableData, String> tableColumn2;

    @FXML
    @Name("Value")
    private TableColumn<TableData, String> tableColumn3;

    @FXML
    private Button createObject_bt;

    @FXML
    private Button deleteObject_bt;

    private ObservableList<Class> classObservableList;
    private ObservableList<TableData> tableDataObservableList;
    private ObservableList bean1ObservableList;
    private ObservableList bean2ObservableList;
    private ObservableList bean3ObservableList;

    public void initialize() {
        classObservableList = FXCollections.observableArrayList();
        try {
            classObservableList.add(Class.forName("model.Bean1"));
            classObservableList.add(Class.forName("model.Bean2"));
            classObservableList.add(Class.forName("model.Bean3"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        chooseClass_cb.setItems(classObservableList);
        bean1ObservableList = FXCollections.observableArrayList();
        bean2ObservableList = FXCollections.observableArrayList();
        bean3ObservableList = FXCollections.observableArrayList();
        addDataToTable();
        tableView.setEditable(true);
        tableColumn3.setCellFactory(TextFieldTableCell.forTableColumn());
        annotations();
    }

    private void annotations() {
        Class clazz = Controller.class;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Name.class)) {
                if (field.getType().equals(TableColumn.class)) {
                    try {
                        Name nameAnnotation = field.getAnnotation(Name.class);
                        TableColumn tableColumn = (TableColumn) field.get(this);
                        tableColumn.setText(nameAnnotation.value());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addDataToTable() {
        tableDataObservableList = FXCollections.observableArrayList();
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("no"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("fieldValue"));
        tableView.setItems(tableDataObservableList);
    }

    @FXML
    void chooseClass_cb_onAction(ActionEvent event) {
        Class aClass = chooseClass_cb.getSelectionModel().getSelectedItem();

        try {
            if (aClass.equals(Class.forName("model.Bean1")))
                chooseObject_cb.setItems(bean1ObservableList);
            else if (aClass.equals(Class.forName("model.Bean2")))
                chooseObject_cb.setItems(bean2ObservableList);
            else if (aClass.equals(Class.forName("model.Bean3")))
                chooseObject_cb.setItems(bean3ObservableList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = aClass.getDeclaredFields();
        Method[] methods = aClass.getDeclaredMethods();
        AtomicInteger runCount = new AtomicInteger(1);

        tableDataObservableList.clear();
        Method getterMethod;
        Method setterMethod;

        for (Field field : fields) {
            String methodName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            getterMethod = Arrays.stream(methods).filter(method -> method.getName().contains("get" + methodName)).findFirst().get();
            setterMethod = Arrays.stream(methods).filter(method -> method.getName().contains("set" + methodName)).findFirst().get();
            tableDataObservableList.add(new TableData(runCount.getAndIncrement(),
                    field, field.getName(), "", getterMethod, setterMethod));
        }
        tableView.setItems(tableDataObservableList);
    }

    @FXML
    void chooseObject_cb_onAction(ActionEvent event) {
        Object object = chooseObject_cb.getSelectionModel().getSelectedItem();
        if (object != null) {
            for (int i = 0; i < tableDataObservableList.size(); i++) {
                try {
                    TableData tableData = tableDataObservableList.get(i);
                    Method method = tableData.getGetterMethod();
                    method.setAccessible(true);
                    Object returnValue = Optional.ofNullable(method.invoke(object))
                            .orElse("");
                    tableData.setFieldValue(returnValue.toString());
                    tableDataObservableList.set(i, tableData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            tableView.setItems(tableDataObservableList);
        }
    }

    @FXML
    void createObjectOnAction(ActionEvent event) {
        try {
            Class<?> aClass = Class.forName(chooseClass_cb.getSelectionModel().getSelectedItem().getName());
            Object object = aClass.newInstance();
            chooseObject_cb.getItems().add(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteObjectOnAction(ActionEvent event) {
        Object object = chooseObject_cb.getSelectionModel().getSelectedItem();
        chooseObject_cb.getItems().remove(object);
    }


    public void onEditCommit_column3(TableColumn.CellEditEvent<TableData, String> tableDataStringCellEditEvent) {
        try {
            String newValue = tableDataStringCellEditEvent.getNewValue();
            Object object = chooseObject_cb.getSelectionModel().getSelectedItem();
            Method method = tableView.getSelectionModel().getSelectedItem().getSetterMethod();
            method.setAccessible(true);

            Integer parametersNumber = method.getParameterCount();
            Parameter[] parameters = method.getParameters();

            if (parametersNumber == 1) {
                Class<?> parameterType = parameters[0].getType();
                Object setterParameter = null;

                if (parameterType.equals(int.class)) {
                    setterParameter = Integer.parseInt(newValue);
                } else if (parameterType.equals(byte.class)) {
                    setterParameter = Byte.parseByte(newValue);
                } else if (parameterType.equals(short.class)) {
                    setterParameter = Short.parseShort(newValue);
                } else if (parameterType.equals(long.class)) {
                    setterParameter = Long.parseLong(newValue);
                } else if (parameterType.equals(float.class)) {
                    setterParameter = Float.parseFloat(newValue);
                } else if (parameterType.equals(double.class)) {
                    setterParameter = Double.parseDouble(newValue);
                } else if (parameterType.equals(boolean.class)) {
                    setterParameter = Boolean.parseBoolean(newValue);
                } else if (parameterType.equals(char.class)) {
                    setterParameter = newValue.charAt(0);
                } else if (parameterType.equals(String.class)) {
                    setterParameter = newValue;
                } else if (parameterType.equals(LocalDate.class)) {
                    setterParameter = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else if (parameterType.isEnum()) {
                    try {
                        Method method1 = parameterType.getMethod("values");
                        Object[] objects = (Object[]) method1.invoke(null);
                        List<String> stringList = new ArrayList<>();
                        for (Object o : objects) {
                            stringList.add(o.toString());
                        }
                        if (stringList.contains(newValue))
                            setterParameter = Enum.valueOf((Class<Enum>) parameterType, newValue);
                        else
                            System.out.println("Wrong value for Enum!");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                method.invoke(object, setterParameter);

                for (int i = 0; i < tableDataObservableList.size(); i++) {
                    TableData tableData = tableDataObservableList.get(i);
                    if (tableData.getSetterMethod().equals(method)) {
                        Method getterMethod = tableData.getGetterMethod();
                        method.setAccessible(true);
                        Object o = getterMethod.invoke(object);
                        if (o != null) {
                            tableData.setFieldValue(getterMethod.invoke(object).toString());
                            tableDataObservableList.set(i, tableData);
                        } else {
                            tableData.setFieldValue("");
                            tableDataObservableList.set(i, tableData);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
