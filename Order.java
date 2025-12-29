package ra.entity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Order {
    private static int nextId = 1; 

    private int orderId;
    private String customerName;
    private String phoneNumber;
    private String address;
    private float orderAmount;
    private OrderStatus status;

    public Order() {
        this.orderId = nextId++;
        this.status = OrderStatus.PENDING;
    }

    public Order(int orderId, String customerName, String phoneNumber, String address, float orderAmount, OrderStatus status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderAmount = orderAmount;
        this.status = status;
    }


    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public float getOrderAmount() { return orderAmount; }
    public void setOrderAmount(float orderAmount) { this.orderAmount = orderAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public void inputData(Scanner scanner) {
        // 1. Nhập tên khách hàng (6-100 ký tự)
        while (true) {
            System.out.print("Nhập tên khách hàng (6-100 ký tự): ");
            String inputName = scanner.nextLine().trim();
            if (inputName.length() >= 6 && inputName.length() <= 100) {
                this.customerName = inputName;
                break;
            } else {
                System.err.println("Tên khách hàng phải từ 6 đến 100 ký tự!");
            }
        }

        while (true) {
            System.out.print("Nhập số điện thoại (VN): ");
            String inputPhone = scanner.nextLine().trim();
            if (inputPhone.matches("^0\\d{9}$")) {
                this.phoneNumber = inputPhone;
                break;
            } else {
                System.err.println("Số điện thoại không hợp lệ (Phải bắt đầu bằng 0 và có 10 số)!");
            }
        }

        while (true) {
            System.out.print("Nhập địa chỉ giao hàng: ");
            String inputAddress = scanner.nextLine().trim();
            if (!inputAddress.isEmpty()) {
                this.address = inputAddress;
                break;
            } else {
                System.err.println("Địa chỉ không được để trống!");
            }
        }

        while (true) {
            System.out.print("Nhập giá trị đơn hàng (>0): ");
            try {
                float inputAmount = Float.parseFloat(scanner.nextLine());
                if (inputAmount > 0) {
                    this.orderAmount = inputAmount;
                    break;
                } else {
                    System.err.println("Giá trị đơn hàng phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số thực hợp lệ!");
            }
        }

    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return String.format("ID: %-5d | KH: %-15s | SĐT: %-12s | ĐC: %-15s | Giá: %-12s | Trạng thái: %s",
                orderId, customerName, phoneNumber, address, nf.format(orderAmount), status);
    }
}
