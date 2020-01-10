package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    public static List<Bucket> buckets = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

}
