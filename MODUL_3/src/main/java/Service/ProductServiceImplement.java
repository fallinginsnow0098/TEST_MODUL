package Service;

import DAO.ProductDAO;
import DAO.IDAO;
import Model.Product;

import java.util.List;

public class ProductServiceImplement implements IService<Product> {
    private final IDAO<Product> productDAO = new ProductDAO();
    @Override
    public boolean add(Product product, int categoryId) {
        return productDAO.add(product, categoryId);
    }

    @Override
    public boolean update(Product product,int categoryId) {
        return productDAO.update(product,categoryId);
    }

    @Override
    public boolean delete(int id) {
        return productDAO.delete(id);
    }

    @Override
    public List<Product> getAll() {
        return productDAO.getAll();
    }

    @Override
    public Product get(int id) {
        return productDAO.get(id);
    }


}
