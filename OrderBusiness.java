package ra.business;

import ra.entity.Order;
import ra.entity.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class OrderBusiness {
    private static List<Order> orders = new ArrayList<>();

    public static void addOrder(Scanner scanner) {
        System.out.println("--- THÊM ĐƠN HÀNG MỚI ---");
        Order newOrder = new Order();
        newOrder.inputData(scanner);
        orders.add(newOrder);
        System.out.println("Thêm đơn hàng thành công! Trạng thái: Pending");
    }
    public static void displayOrders() {
        System.out.println("--- DANH SÁCH ĐƠN HÀNG ---");
        if (orders.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        orders.sort((o1, o2) -> Float.compare(o2.getOrderAmount(), o1.getOrderAmount()));
        
        for (Order o : orders) {
            System.out.println(o.toString());
        }
    }

    public static void updateOrderStatus(Scanner scanner) {
        System.out.print("Nhập mã đơn hàng cần cập nhật: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Optional<Order> optOrder = orders.stream().filter(o -> o.getOrderId() == id).findFirst();

            if (optOrder.isPresent()) {
                Order order = optOrder.get();
                OrderStatus currentStatus = order.getStatus();
                
                System.out.println("Trạng thái hiện tại: " + currentStatus);
                
                if (currentStatus == OrderStatus.DELIVERED) {
                    System.err.println("Đơn hàng đã giao thành công (Delivered), không thể cập nhật nữa!");
                    return;
                }
                OrderStatus nextStatus = null;
                if (currentStatus == OrderStatus.PENDING) {
                    nextStatus = OrderStatus.SHIPPED;
                } else if (currentStatus == OrderStatus.SHIPPED) {
                    nextStatus = OrderStatus.DELIVERED;
                }

                System.out.print("Bạn có muốn chuyển sang trạng thái " + nextStatus + " không? (Y/N): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    order.setStatus(nextStatus);
                    System.out.println("Cập nhật trạng thái thành công!");
                }
            } else {
                System.err.println("Không tìm thấy mã đơn hàng: " + id);
            }
        } catch (NumberFormatException e) {
            System.err.println("Mã đơn hàng phải là số nguyên!");
        }
    }
    public static void deleteOrder(Scanner scanner) {
        System.out.print("Nhập mã đơn hàng cần xóa: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Optional<Order> optOrder = orders.stream().filter(o -> o.getOrderId() == id).findFirst();

            if (optOrder.isPresent()) {
                Order order = optOrder.get();
                if (order.getStatus() == OrderStatus.PENDING) {
                    System.out.print("Xác nhận xóa đơn hàng này? (Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("Y")) {
                        orders.remove(order);
                        System.out.println("Xóa thành công!");
                    }
                } else {
                    System.err.println("Chỉ được xóa đơn hàng ở trạng thái Pending!");
                }
            } else {
                System.err.println("Không tìm thấy mã đơn hàng: " + id);
            }
        } catch (NumberFormatException e) {
            System.err.println("Mã đơn hàng phải là số nguyên!");
        }
    }
    public static void searchByCustomerName(Scanner scanner) {
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        
        List<Order> results = orders.stream()
                .filter(o -> o.getCustomerName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy kết quả nào.");
        } else {
            System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
            results.forEach(System.out::println);
        }
    }
    public static void statsTotalOrders() {
        System.out.println("Tổng số đơn hàng hiện có: " + orders.size());
    }

    public static void statsRevenueDelivered() {
        double revenue = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getOrderAmount)
                .sum();
        System.out.printf("Tổng doanh thu các đơn Delivered: %,.0f VND\n", revenue);
    }

    public static void statsCountByStatus() {
        long pending = orders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count();
        long shipped = orders.stream().filter(o -> o.getStatus() == OrderStatus.SHIPPED).count();
        long delivered = orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).count();

        System.out.println("--- THỐNG KÊ TRẠNG THÁI ---");
        System.out.println("Pending:   " + pending);
        System.out.println("Shipped:   " + shipped);
        System.out.println("Delivered: " + delivered);
    }

    public static void findMaxOrder() {
        Optional<Order> maxOrder = orders.stream()
                .max(Comparator.comparingDouble(Order::getOrderAmount));
        
        if (maxOrder.isPresent()) {
            System.out.println("Đơn hàng có giá trị lớn nhất:");
            System.out.println(maxOrder.get());
        } else {
            System.out.println("Danh sách trống.");
        }
    }
}
