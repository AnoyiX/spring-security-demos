package spring4all.service;

import org.springframework.stereotype.Service;
import spring4all.entity.UserEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    // simulate database
    private static Map<String, UserEntity> users  = new ConcurrentHashMap<>();

    static {
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword("pwd");
        admin.setRoles("ADMIN");
        admin.setPermissions("Read", "Write", "Delete");

        UserEntity sales = new UserEntity();
        sales.setUsername("sales");
        sales.setPassword("pwd");
        sales.setRoles("SALES_REP");
        sales.setPermissions("Read");

        UserEntity order = new UserEntity();
        order.setUsername("order");
        order.setPassword("pwd");
        order.setRoles("ORDER_REP");
        order.setPermissions("Read", "Write");

        users.put(admin.getUsername(), admin);
        users.put(sales.getUsername(), sales);
        users.put(order.getUsername(), order);
    }

    /**
     * Get user info by username
     */
    public UserEntity getByUsername(String username){
        return users.get(username);
    }


}
