package startFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Controller {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mess";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "taeven";

    private ObservableList<ListView> studentDetails = FXCollections.observableArrayList();

    private ObservableList<ListView> messDetails = FXCollections.observableArrayList();

    private ObservableList<ListView> menuDetails = FXCollections.observableArrayList();
    private static Connection conn = null;
    private static Statement stmt = null;


    @FXML
    Pane addStudent, student, mess, addMess, menu;

    @FXML
    RadioButton male, female;

    @FXML
    TextField name, roll, email, dob, rollToRemove, prefMess;


    public Controller(){

        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();




        }catch(SQLException | ClassNotFoundException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
//        ToggleGroup tg = new ToggleGroup();
//        male.setToggleGroup(tg);
//        female.setToggleGroup(tg);
    }
    void setfalse(){
        addStudent.setVisible(false);
        addMess.setVisible(false);
        student.setVisible(false);
        mess.setVisible(false);
        menu.setVisible(false);
    }

    void clearStudent(){
        name.clear();
        email.clear();
        dob.clear();
        roll.clear();
        male.setSelected(false);
        female.setSelected(false);
        prefMess.clear();
    }
    public void addStudent(){
        setfalse();
        addStudent.setVisible(true);
    }

    public void studentDetails(){
        setfalse();
        student.setVisible(true);

        studentList.setItems(studentDetails);
        readyStudentList();
    }
    public void onRemove(){
        String rollT=rollToRemove.getText();
        if(!rollT.isEmpty()){
            String query = "delete from student where roll_no like '"+rollT+"';";
            try {
                stmt.executeUpdate(query);
                rollToRemove.clear();
                studentStatus.setText("student removed!");
            } catch (SQLException e) {
                studentStatus.setText("cannot remove student");
                e.printStackTrace();

            }
        }

    }
    private ArrayList<Student> getStudents()
    {
        ArrayList<Student> list = new ArrayList<Student>();
        String query = "select student.roll_no as roll_no, student.name as name," +
                " sex, dob, email, stud_mess.name as mess from student join stud_mess " +
                "on student.roll_no = stud_mess.roll_no;";

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Student tmp = new Student();
                tmp.roll=(rs.getString("roll_no"));

                tmp.name=(rs.getString("name"));
                tmp.dob=(rs.getString("dob"));
                tmp.gender=(rs.getString("sex"));
                tmp.email=(rs.getString("email"));
                tmp.mess = rs.getString("mess");

                list.add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    ListView menuList;



    public void updateMenu(){

        Thread th = new Thread(new Runnable() {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            @Override
            public void run() {
                date = new Date(System.currentTimeMillis());
                if(date.getHours()==0 && date.getMinutes()==1){
//                    try{
//
//                    }
                }
            }
        });
        th.setDaemon(true);
    }

    public void menuDetails(){
        setfalse();
        menu.setVisible(true);
        readyMenuList();
        menuList.setItems(menuDetails);

    }
    public void readyMenuList(){

        ArrayList<Mess> from_db = getMess();
        ArrayList<Menu> menus = getMenu();

        int cnt =0;
        if(!from_db.isEmpty()) {
            cnt++;
            menuDetails.clear();
            while (!from_db.isEmpty()) {
                //FOR TITLE
                ListView<String> title = new ListView<String>();
                title.setOrientation(Orientation.HORIZONTAL);

                title.setMaxHeight(40);
                title.setMouseTransparent(true);
                title.setFocusTraversable(true);
                title.setPrefHeight(40);
                ObservableList<String> title_List = FXCollections.observableArrayList();
                title.setItems(title_List);
                title.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };


                        lc.setPrefWidth(900);
                        return lc;
                    }

                });

                Mess tmp = from_db.remove(0);
                title_List.add(tmp.name);
                menuDetails.add(title);
                //FOR ELEMENT;

                ListView<String> single_row = new ListView<String>();
                single_row.setOrientation(Orientation.HORIZONTAL);

                single_row.setMaxHeight(40);
                single_row.setMouseTransparent(true);
                single_row.setFocusTraversable(true);
                single_row.setPrefHeight(40);
                ObservableList<String> single = FXCollections.observableArrayList();
                single_row.setItems(single);
                single_row.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(200);
                        return lc;
                    }

                });

                    single.add("Time");
                    single.add("Food1");
                    single.add("Food2");
                    single.add("Food3");
                    menuDetails.add(single_row);





                ListView<String> breakfast = new ListView<String>();
                breakfast.setOrientation(Orientation.HORIZONTAL);

                breakfast.setMaxHeight(40);
                breakfast.setMouseTransparent(true);
                breakfast.setFocusTraversable(true);
                breakfast.setPrefHeight(40);
                ObservableList<String> bcell = FXCollections.observableArrayList();
                breakfast.setItems(bcell);
                breakfast.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(200);
                        return lc;
                    }

                });


                bcell.add("Breakfast");
                bcell.add(menus.get(cnt).b1);

                bcell.add(menus.get(cnt).b2);

                bcell.add(menus.get(cnt).b3);
                menuDetails.add(breakfast);








                ListView<String> lunch = new ListView<String>();
                lunch.setOrientation(Orientation.HORIZONTAL);

                lunch.setMaxHeight(40);
                lunch.setMouseTransparent(true);
                lunch.setFocusTraversable(true);
                lunch.setPrefHeight(40);
                ObservableList<String> lcell = FXCollections.observableArrayList();
                lunch.setItems(lcell);
                lunch.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(200);
                        return lc;
                    }

                });


                lcell.add("Lunch");
                lcell.add(menus.get(cnt).l1);
                lcell.add(menus.get(cnt).l2);
                lcell.add(menus.get(cnt).l3);

                menuDetails.add(lunch);









                ListView<String> dinner = new ListView<String>();
                dinner.setOrientation(Orientation.HORIZONTAL);

                dinner.setMaxHeight(40);
                dinner.setMouseTransparent(true);
                dinner.setFocusTraversable(true);
                dinner.setPrefHeight(40);
                ObservableList<String> dcell = FXCollections.observableArrayList();
                dinner.setItems(dcell);
                dinner.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(200);
                        return lc;
                    }

                });

                dcell.add("Dinner");
                dcell.add(menus.get(cnt).d1);
                dcell.add(menus.get(cnt).d2);
                dcell.add(menus.get(cnt).d3);
                menuDetails.add(dinner);

                ListView<String> diblank = new ListView<String>();

                diblank.setMaxHeight(40);
                diblank.setMouseTransparent(true);
                diblank.setFocusTraversable(true);
                diblank.setPrefHeight(40);
                menuDetails.add(diblank);

            }
        }
    }

    public ArrayList<Menu> getMenu()
    {
        ArrayList<Menu> list = new ArrayList<Menu>();
        String query = "select m_id, breakfast.food1 as b1, breakfast.food2 as b2, breakfast.food3 as b3, lunch.food1 as l1, " +
                "lunch.food2 as l2, lunch.food3 as l3, dinner.food1 as d1, dinner.food2 as d2, dinner.food3 as d3 from menu join " +
                "dinner on menu.d_id=dinner.d_id join breakfast on menu.b_id=breakfast.b_id join " +
                "lunch on lunch.l_id = menu.l_id;";

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Menu tmp = new Menu();
                tmp.b1=(rs.getString("b1"));
                tmp.b2=(rs.getString("b2"));
                tmp.b3=(rs.getString("b3"));
                tmp.l1=(rs.getString("l1"));
                tmp.l2=(rs.getString("l2"));
                tmp.l3=(rs.getString("l3"));
                tmp.d1=(rs.getString("d1"));
                tmp.d2=(rs.getString("d2"));
                tmp.d3=(rs.getString("d3"));
                tmp.m=(rs.getString("m_id"));



                list.add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void clickSubmit(){
        String nameT = name.getText();
        String emailT,dobT,rollT;
        emailT=email.getText();
        dobT = dob.getText();
        rollT = roll.getText();
        String messT = prefMess.getText();
        if(emailT.isEmpty() || dobT.isEmpty() || rollT.isEmpty() || nameT.isEmpty() ||messT.isEmpty()|| (!male.isSelected()&&!female.isSelected()))
        {
            studentStatus.setText("Fill all the fields!!");
        }
        else{
            String gender= male.isSelected()?"M":"F";




            String query="insert into student values('"+rollT+"','"+nameT+"','"+gender+"','"+dobT+"','"+emailT+"');";
            try {
                stmt.executeUpdate(query);
                try {


                    String query1 = "insert into stud_mess values('" +rollT+"','"+messT+"');";
                    stmt.executeUpdate(query1);
                    clearStudent();
                    studentStatus.setText("student added to database");
                } catch (SQLException e) {
                    e.printStackTrace();

                    String query2 = "delete from student where roll_no= '"+rollT+"';";
                    stmt.executeUpdate(query2);
                    studentStatus.setText("insertion failed!");

                }

            } catch (SQLException e) {
                e.printStackTrace();
                studentStatus.setText("insertion failed!");
            }
        }
    }

    @FXML
    Label studentStatus;
    @FXML
    ListView studentList;

    private void readyStudentList()
    {

        ArrayList<Student> from_db = getStudents();

        boolean first = true;
        if(!from_db.isEmpty()) {
            studentDetails.clear();
            while (!from_db.isEmpty()) {

                ListView<String> single_row = new ListView<String>();
                single_row.setOrientation(Orientation.HORIZONTAL);

                single_row.setMaxHeight(40);
                single_row.setMouseTransparent(true);
                single_row.setFocusTraversable(true);
                single_row.setPrefHeight(40);
                ObservableList<String> single = FXCollections.observableArrayList();
                single_row.setItems(single);
                single_row.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(150);
                        return lc;
                    }

                });
                //setting headers
                if (first) {
                    single.add("Roll no.");
                    single.add("Name");
                    single.add("Gender");
                    single.add("DOB");
                    single.add("Email");
                    single.add("Mess");

                    first = false;

                }
                //retrieving data
                else {
                    Student tmp = from_db.remove(0);
                    single.add(tmp.roll);
                    single.add(tmp.name);
                    single.add(tmp.gender);
                    single.add(tmp.dob);
                    single.add(tmp.email);
                    single.add(tmp.mess);


                }
                studentDetails.add(single_row);
            }
        }


    }
    @FXML
    public TextField messName, contractor,messLocation, breakfast, lunch, dinner, messEmail, phone, messMenu, messNameRemove;

    public void addMess(){
        setfalse();
        addMess.setVisible(true);

    }

    private void clearMess(){
        messName.clear();
        contractor.clear();
        messLocation.clear();
        breakfast.clear();
        lunch.clear();
        dinner.clear();
        messEmail.clear();
        phone.clear();
        messMenu.clear();
        messNameRemove.clear();

    }
    public void addMessDetails(){
        String name = messName.getText();
        String contract = contractor.getText();
        String loc = messLocation.getText();
        String breakfst = breakfast.getText();
        String lun = lunch.getText();
        String dinn = dinner.getText();
        String email = messEmail.getText();
        String phn = phone.getText();
        String messMen = messMenu.getText();
        if(name.isEmpty()|| contract.isEmpty()||loc.isEmpty()||breakfst.isEmpty()||lun.isEmpty()||dinn.isEmpty()||email.isEmpty()||phn.isEmpty()||
                messMen.isEmpty()){}
                else{
            String query = "insert into mess values ('"+name+"','"+
                    contract+"','"+
                    loc+"','"+
                    breakfst+"','"+
                    lun+"','"+
                    dinn+"','"+
                    email+"','"+
                    phn+"','"+
                    messMen+"');";

            try {
                stmt.executeUpdate(query);
                clearMess();
                messStatus.setText("Mess added to database");
            } catch (SQLException e) {
                e.printStackTrace();
                messStatus.setText("insertion failed!");
            }

        }
    }
    @FXML
    Label messStatus;

    @FXML
    ListView messList;

    public void showMessDetails(){

        setfalse();
        mess.setVisible(true);
        readyMessList();
        messList.setItems(messDetails);


    }

    private ArrayList<Mess> getMess()
    {
        ArrayList<Mess> list = new ArrayList<Mess>();
        String query = "select * from mess;";

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Mess tmp = new Mess();
                tmp.name=(rs.getString("name"));

                tmp.contractor=(rs.getString("contractor"));
                tmp.location=(rs.getString("location"));
                tmp.breakfast=(rs.getString("b_timing"));
                tmp.lunch=(rs.getString("l_timing"));

                tmp.dinner=(rs.getString("d_timing"));
                tmp.email=(rs.getString("email"));
                tmp.phone=(rs.getString("phone"));
                tmp.menu=(rs.getString("menu"));


                list.add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    public void readyMessList()
    {

        ArrayList<Mess> from_db = getMess();

        boolean first = true;
        if(!from_db.isEmpty()) {
            messDetails.clear();
            while (!from_db.isEmpty()) {

                ListView<String> single_row = new ListView<String>();
                single_row.setOrientation(Orientation.HORIZONTAL);

                single_row.setMaxHeight(40);
                single_row.setMouseTransparent(true);
                single_row.setFocusTraversable(true);
                single_row.setPrefHeight(40);
                ObservableList<String> single = FXCollections.observableArrayList();
                single_row.setItems(single);
                single_row.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell lc = new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(null);
                                if (!empty) {
                                    setText(item);
                                }
                            }
                        };
                        lc.setPrefWidth(101);
                        return lc;
                    }

                });
                //setting headers
                if (first) {
                    single.add("Name");
                    single.add("contractor");
                    single.add("location");
                    single.add("Breakfast time");
                    single.add("Lunch time");
                    single.add("Dinner time");
                    single.add("email");
                    single.add("phone");
                    single.add("menu");



                    first = false;

                }
                //retrieving data
                else {
                    Mess tmp = from_db.remove(0);
                    single.add(tmp.name);
                    single.add(tmp.contractor);
                    single.add(tmp.location);
                    single.add(tmp.breakfast);
                    single.add(tmp.lunch);

                    single.add(tmp.dinner);
                    single.add(tmp.email);
                    single.add(tmp.phone);
                    single.add(tmp.menu);


                }
                messDetails.add(single_row);
            }
        }


    }
    public void removeMess(){
        String name = messNameRemove.getText();
        if(!name.isEmpty()){
            String query = "delete from mess where name = '"+name+"'";
            try {
                stmt.executeUpdate(query);
                messNameRemove.clear();
                messStatus.setText("Mess deleted from database!");

            } catch (SQLException e) {
                e.printStackTrace();
                messStatus.setText("Deletion failed!");
            }

        }
    }


}
