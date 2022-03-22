package Controller;

import DAO.IDAO;
import DAO.ProductDAO;
import Model.Product;
import Service.IService;
import Service.ProductServiceImplement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Controller.Servlet", urlPatterns = "/home")
public class ProductServlet extends HttpServlet {
    private final IService<Product> productIService = new ProductServiceImplement();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request,response);
    }

    private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action){
            case "add":
                addProduct(request,response);
                break;
            case "showFormAdd":
                addGet(request,response);
                break;
            case "update":
                updateProduct(request,response);
                break;
            case "showFormUpdate":
                updateGet(request,response);
                break;
            case "delete":
                deleteById(request,response);
                break;
            default:
                display(request,response);
        }
    }

    private void addGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productIService.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/view/create.jsp").forward(request,response);
    }

    private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productIService.get(id);
        List<Product> products = productIService.getAll();
        request.setAttribute("product", product);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/view/update.jsp");
    }


    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productIService.delete(id);
        List<Product> products = productIService.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/view/home.jsp");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productIService.getAll();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productIService.get(id);
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id){
                index = i;
            }
        }
        boolean checkUpdate = productIService.update(new Product(name, price, color, description, quantity), categoryId);
        products = productIService.getAll();
        request.setAttribute("products", products);
        request.setAttribute( "checkUpdate", checkUpdate);
        request.getRequestDispatcher("/view/home.jsp");
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = new Product(name, price, color, description, quantity);
        boolean checkAdd = productIService.add(product, categoryId);
        request.setAttribute("checkAdd", checkAdd);
        request.getRequestDispatcher("/view/create.jsp").forward(request,response);
    }

    private void display(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productIService.getAll();
        request.setAttribute("product", products);
        request.getRequestDispatcher("/view/home.jsp").forward(request,response);
    }
}
