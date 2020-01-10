import java.util.List;

import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class App {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Item item1 = new Item();
        item1.setItemName("Item1");
        item1.setItemPrice(0.1);
        Item item2 = new Item();
        item2.setItemName("Item2");
        item1.setItemPrice(0.1);
        Item item3 = new Item();
        item3.setItemName("Item3");
        item1.setItemPrice(0.1);

        User user1 = new User();
        user1.setName("User1");
        User user2 = new User();
        user2.setName("User2");

        itemService.create(item1);
        itemService.create(item2);
        itemService.create(item3);
        List<Item> items = itemService.getAllItem();
        System.out.println("Item Service test");
        for (Item item : items) {
            System.out.println(item.getItemName() + " id : " + item.getItemId());
        }

        Bucket bucket1 = new Bucket();
        bucket1.setUser(user1.getUserId());
        bucket1.setItem(item1);
        bucket1.setItem(item2);

        Bucket bucket2 = new Bucket();
        bucket2.setUser(user2.getUserId());
        bucket2.setItem(item1);
        bucket2.setItem(item2);
        bucket2.setItem(item3);

        bucketService.create(bucket1);
        bucketService.create(bucket2);

        System.out.println("Bucket Service test");
        List<Item> bucketsList1 = bucketService.getAllItems(bucket1);
        for (Item item : items) {
            System.out.println(item.getItemName() + " id : " + item.getItemId());
        }

        List<Item> bucketsList2 = bucketService.getAllItems(bucket2);
        for (Item item : items) {
            System.out.println(item.getItemName() + " id : " + item.getItemId());
        }

        System.out.println("User Service test");
        User user3 = new User();
        user3.setName("User3");
        User user4 = new User();
        user4.setName("User4");

        userService.create(user3);
        userService.create(user4);
        for (int i = 0; i < Storage.users.size(); i++) {
            System.out.println(Storage.users.get(i).getName()
                    + " " + Storage.users.get(i).getUserId());
        }
        System.out.println(user1.getName());
        System.out.println(user3.getName());

        orderService.completeOrder(bucketService.getAllItems(bucket2), user4);
    }
}
