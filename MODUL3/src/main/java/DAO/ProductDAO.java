package DAO;

import DatabaseConnection.DatabaseConnection;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDAO<Product>{
    @Override
    public boolean add(Product product, int categoryId) {
        boolean rowAdded = false;
        try {
            Connection connection= DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO product (`name`, `price`, `color`, `description`, `category`, `quantity`) VALUES (?,?,?,?,?,?);");
            ps.setString(1,product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getColor());
            ps.setString(4, product.getDescription());
            ps.setInt(5, categoryId);
            ps.setInt(6, product.getQuantity());
            rowAdded = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ADD PRODUCT FAIL");
        }
        return rowAdded;
    }

    @Override
    public boolean update(Product product, int categoryId ){
        boolean rowUpdate = false;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE `productmanager`.`product` SET `name` = ?, `price` = ?, `color` = ?, `description` = ?, `category` = ? WHERE (`id` = ?);");
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getColor());
            ps.setString(4, product.getDescription());
            ps.setInt(5, categoryId);
            ps.setInt(6, product.getId());
            rowUpdate = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("UPDATE FAIL");
        }
        return rowUpdate;
    }

    @Override
    public boolean delete(int id) {
        boolean rowDel = false;
        try {
          Connection connection = DatabaseConnection.getConnection();
          PreparedStatement ps = connection.prepareStatement("DELETE FROM product WHERE (`id` = ?);");
          ps.setInt(1, id);
          rowDel = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DELETE PRODUCT FAIL");
        }
        return rowDel;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT p.name, p.price, p.quantity, p.color, p.description, c.name FROM product p JOIN category c ON p.category = c.id;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                int quantity = rs.getInt(3);
                String color = rs.getString(4);
                String description = rs.getString(5);
                String category = rs.getString(6);
                products.add(new Product(name,price,quantity,color,description,category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi lấy product");
        }
        return products;
    }

    @Override
    public Product get(int id) {
        Product product = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT p.name, p.price, p.quantity, p.color, p.description, c.name FROM product p JOIN category c ON p.category = c.id WHERE p.id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString(1);
                double price = rs.getDouble(2);
                int quantity = rs.getInt(3);
                String color = rs.getString(4);
                String description = rs.getString(5);
                String category = rs.getString(6);
                product = new Product(name, price,quantity,color,description,category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}
