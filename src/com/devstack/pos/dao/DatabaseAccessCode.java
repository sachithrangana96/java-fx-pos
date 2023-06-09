package com.devstack.pos.dao;
import com.devstack.pos.dao.custome.CustomerDao;
import com.devstack.pos.dao.custome.ProductDao;
import com.devstack.pos.dao.custome.UserDao;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.UserDto;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.Product;
import com.devstack.pos.entity.User;
import com.devstack.pos.enums.DaoType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {


    CustomerDao customerDao =  DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    ProductDao productDao =  DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    UserDao userDao =  DaoFactory.getInstance().getDao(DaoType.USER);

//    User Management
    public  boolean createUser(String email,String password ) throws ClassNotFoundException, SQLException {
       return userDao.save(
               new User(
                       email,
                       password
               )
       );
    }


    public  UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        User user = userDao.find(email);
        if(user!=null){
            return new UserDto(
                    user.getEmail(),
                    user.getPassword()
            );
        }
        return null;
    }
    //    User Management


//    Customer Managment

    public  boolean createCustomer(String email,String name,String contact,double salary) throws ClassNotFoundException, SQLException {
       return customerDao.save(
               new Customer(
                       email,
                       name,
                       contact,
                       salary
               )
       );
    }

    public  boolean updateCustomer(String email,String name,String contact,double salary) throws ClassNotFoundException, SQLException {
        return customerDao.update(
                new Customer(
                        email,
                        name,
                        contact,
                        salary
                )
        );
    }

    public  CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Customer customer = customerDao.find(email);
        if(customer!=null){
            return new CustomerDto(
                   customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
                    );
        }
        return null;
    }

    public  boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
       return customerDao.delete(email);

    }


    public  List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer c:customerDao.findAll()){
            dtos.add(new CustomerDto(
                        c.getEmail(),
                        c.getName(),
                        c.getContact(),
                        c.getSalary()
            ));
        }
        return dtos;
    }

    public  List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        searchText="%"+searchText+"%";
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer c:customerDao.searchCustomer(searchText)){
            dtos.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return dtos;
    }
    //    Customer Managment


    //    Product Managment
     public  int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductId();
     }


     public   boolean saveProduct(int code,String description) throws SQLException, ClassNotFoundException {
        return productDao.save(
                new Product(
                        code,
                        description
                )
        );
     }

    //    Product Managment
}
