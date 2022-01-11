package _Manager;

import _Model.Contact;
import _Regex.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Contact> contacts ;
    private final Validate validate = new Validate();
    public static final String PATH_CONTACT = "src/_FileCSV/contact.csv";

    public ContactManager(){
        if (new File(PATH_CONTACT).length() == 0){
            this.contacts = new ArrayList<>();
        } else {
            this.contacts = readFileCSV(PATH_CONTACT);
        }
    }
    public ArrayList<Contact> getContacts(){
        return contacts;
    }
    // đọc ghi fileCSV
    public ArrayList<Contact> readFileCSV(String path){
        ArrayList<Contact> contactList = new ArrayList<>();
        File file = new File(path);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line ;
            while ((line = br.readLine()) != null){
                String[] strings = line.split(",");
                contacts.add(new Contact(strings[0],
                        strings[1],
                        strings[2],
                        strings[3],
                        strings[4],
                        LocalDate.parse(strings[5], DateTimeFormatter.ISO_LOCAL_DATE),
                        strings[6]));
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return contactList;
    }
    public void writeFileCSV(ArrayList<Contact> contactList , String path){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Contact c : contactList) {
                bw.write(c.getPhoneNumber() +"," +
                        c.getGroup() + ","+
                        c.getName() + "," +
                        c.getGender() + "," +
                        c.getAddress() + "," +
                        c.getBirthday() + "," +
                        c.getEmail() +"\n");
            }
            bw.close();
        } catch (IOException e){
            System.out.println("File không tồn tại !! \n Hoặc đọc file bị lỗi!! \n");
        }
    }
    // định dạng email số điện thoại nhập vào
    public String inputEmail(){
        String email;
        while (true){
            System.out.println("Nhập Email của bạn: ");
            String input = scanner.nextLine();
            if (!validate.validateEmail(input)){
                System.out.println("Email vừa nhập sai định dạng!");
                System.out.println("Vui lòng nhập lại theo định dạng sau: abc.cde@gmail.com hoặc yahoo.vn\n");
            } else {
                email = input;
                break;
            }
        }
        return email;
    }
    public String inputPhoneNumber(){
        String phoneNumber;
        while (true){
            System.out.println("Nhập vào số điện thoại của bạn");
            String input = scanner.nextLine();
            if (!validate.validatePhone(input)){
                System.out.println("Số điện thoại vừa nhập sai định dạng!");
                System.out.println("Vui lòng nhập theo định dạng sau: (+84)-123456789 \n");
            } else {
                phoneNumber = input;
                break;
            }
        }
        return phoneNumber;
    }
    public void addContact(){
        System.out.println("Mời nhập thông tin của bạn!\n");
        String phoneNumber = inputPhoneNumber();
        System.out.println("Nhập nhóm của danh bạ");
        String group = scanner.nextLine();
        System.out.println("Nhập họ và tên đầy đủ");
        String name = scanner.nextLine();
        System.out.println("Nhập vào giới tính");
        String gender = scanner.nextLine();
        System.out.println("Nhập vào địa chỉ");
        String address = scanner.nextLine();
        System.out.println("Nhập vào ngày tháng năm sinh (DD-mm-YYYY)");
        String inputDate = scanner.nextLine();
        LocalDate birthday = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
        System.out.println("Nhập vào tài khoản Email");
        String email = inputEmail();
        contacts.add(new Contact(phoneNumber, group, name, gender, address, birthday, email));
        writeFileCSV(contacts, PATH_CONTACT);
        System.out.println("Ghi dữ liệu ra file CSV thành công! \n");
    }
    public void updateContact(String phoneNumber){
        Contact updateContact = null;
        for (Contact c : contacts) {
            if (c.getPhoneNumber().equals(phoneNumber)){
                updateContact = c;
            }
        }
        if (updateContact != null){
            int index = contacts.indexOf(updateContact);
            System.out.println("Nhập vào nhóm danh bạ mới");
            updateContact.setGroup(scanner.nextLine());
            System.out.println("Nhập vào họ tên mới");
            updateContact.setName(scanner.nextLine());
            System.out.println("Nhập vào giới tính mới");
            updateContact.setGender(scanner.nextLine());
            System.out.println("Nhập vào địa chỉ mới");
            updateContact.setAddress(scanner.nextLine());
            System.out.println("Nhập vào Ngày tháng năm sinh mới");
            String inputDate = scanner.nextLine();
            LocalDate editDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            updateContact.setBirthday(editDate);
            updateContact.setEmail(inputEmail());
            contacts.set(index, updateContact);
            System.out.printf("Sửa thông tin của số điện thoại " + phoneNumber + " thành công!\n");
            writeFileCSV(contacts, PATH_CONTACT);
            System.out.println("Ghi dữ liệu ra file CSV thành công! \n");
        }
    }
    public void deleteContactByPhoneNumber(String phoneNumber){
        Contact deleteContact = null;
        for (Contact c : contacts) {
            if (c.getPhoneNumber().equals(phoneNumber)){
                deleteContact = c;
            }
        }
        if (deleteContact != null){
            System.out.println("Bạn có muốn xóa thông tin số điện thoại này?");
            System.out.println("Nhập 'Y' nếu bạn muốn xóa");
            System.out.println("Nhập 'N' nếu bạn không muốn xóa và quay lại Menu");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")){
                contacts.remove(deleteContact);
                System.out.println("Xóa " + phoneNumber + " thành công");
                writeFileCSV(contacts, PATH_CONTACT);
            }
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên!!\n");
        }
    }
    public void searchByKeyword(String keyword){
        ArrayList<Contact> searchContacts = new ArrayList<>();
        for (Contact c : contacts) {
            if ( (validate.validateSearchByKeyword(keyword, c.getPhoneNumber()))|| (validate.validateSearchByKeyword(keyword, c.getName()))){
                searchContacts.add(c);
            }
        }
        if (searchContacts.isEmpty()){
            System.out.println("Không tìm thấy danh bạ với từ khóa trên!\n");
        } else {
            System.out.println("Danh sách danh bạ theo từ khóa trên: ");
            searchContacts.forEach(System.out::println);
            System.out.println();
        }
    }
    public void displayAllContact(){
        System.out.printf("| %-15s| %-10s| %-15s| %-10s| %-10s|\n", "Số điện thoại", "Nhóm danh bạ", "Họ tên", "Giới tính", "Địa chỉ");
        for (Contact contact : contacts) {
            System.out.printf("| %-15s| %-10s| %-15s| %-10s| %-10s|\n", contact.getPhoneNumber(), contact.getGroup(), contact.getName(), contact.getGender(), contact.getAddress());
            System.out.println("-----------------------------------------------------------------------");
        }
    }
}
