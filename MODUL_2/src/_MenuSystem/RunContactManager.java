package _MenuSystem;

import _Manager.ContactManager;
import _Model.Contact;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunContactManager {
    Scanner scanner = new Scanner(System.in);
    ContactManager contactManager = new ContactManager();
    public RunContactManager(){}

    public void menuContact(){
        try {
            do {
                System.out.println("----CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ----");
                System.out.println("Chọn chức năng theo số (để tiếp tục):");
                System.out.println("1. Xem danh sách");
                System.out.println("2. Thêm mới");
                System.out.println("3. Cập nhật");
                System.out.println("4. Xóa");
                System.out.println("5. Tìm kiếm");
                System.out.println("6. Đọc từ file");
                System.out.println("7. Ghi vào file");
                System.out.println("8. Thoát");
                System.out.println("Chọn chức năng:");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    System.out.println();
                    System.out.println("⛔ Lựa chọn không tồn tại, mời bạn nhập lại !!!");
                    System.out.println("--------------------");
                }
                switch (choice) {
                    case 1:
                        contactManager.displayAllContact();
                        break;
                    case 2:
                        contactManager.addContact();
                        break;
                    case 3:
                        System.out.println("▹ Nhập số điện thoại cần sửa (+84)-123456789:");
                        String phoneEdit = scanner.nextLine();
                        if (phoneEdit.equals("")) {
                            menuContact();
                        } else {
                            contactManager.updateContact(phoneEdit);
                        }
                        break;
                    case 4:
                        System.out.println("▹ Nhập số điện thoại cần xóa (+84)-123456789:");
                        String deletePhoneNumber = scanner.nextLine();
                        if (deletePhoneNumber.equals("")) {
                            menuContact();
                        } else {
                            contactManager.deleteContactByPhoneNumber(deletePhoneNumber);
                        }
                        break;
                    case 5:
                        System.out.println("▹ Nhập từ khóa:");
                        String keyword = scanner.nextLine();
                        contactManager.searchByKeyword(keyword);
                        break;
                    case 6:
                        System.out.println("Danh sách danh bạ: ");
                        ArrayList<Contact> contactArrayList = contactManager.readFileCSV(ContactManager.PATH_CONTACT);
                        contactArrayList.forEach(System.out::println);
                        System.out.println();
                        break;
                    case 7:
                        contactManager.writeFileCSV(contactManager.getContacts(), ContactManager.PATH_CONTACT);
                        System.out.println("Ghi dữ liệu ra file CSV thành công! \n");
                        break;
                    case 8:
                        System.exit(8);
                }
            } while (true);
        } catch (NumberFormatException | DateTimeParseException e){
            System.out.println("Dữ liệu vừa nhập không đúng, vui lòng nhập lại!");
            menuContact();
        }
    }
}
