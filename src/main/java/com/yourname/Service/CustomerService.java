package com.yourname.Service;

import com.yourname.Domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Service
public class CustomerService {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public Customer findById(int id) {
        return jdbcTemplate.queryForObject("select * from customer where id=?", new Object[] { id },
                new BeanPropertyRowMapper<Customer>(Customer.class));
    }



    public void deleteById(int id) {
        jdbcTemplate.update("delete from customer where id=?", id);
    }

    public void add(Customer customer) {
        jdbcTemplate.update("insert into customer (id, name, phone) values(?,  ?, ?)",
                customer.getId(), customer.getName(), customer.getPhone());
    }

    public void update(Customer customer)
        {
             jdbcTemplate.update("update customer set id = ?, set name = ?, phone = ? where id = ?",
                     new Object[] { customer.getId(), customer.getName(), customer.getPhone(), customer.getId()});
        }

    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int i) throws SQLException {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                return customer;
            }
        });
    }
}
