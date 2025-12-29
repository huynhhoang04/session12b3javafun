package ra.presentation;

import ra.business.OrderBusiness;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n**************** QUẢN LÝ ĐƠN HÀNG ****************");
            System.out.println("1. Thêm đơn hàng");
            System.out.println("2. Hiển thị danh sách đơn hàng");
            System.out.println("3. Cập nhật trạng thái đơn hàng theo mã đơn hàng");
            System.out.println("4. Xóa đơn hàng theo mã đơn hàng");
            System.out.println("5. Tìm kiếm đơn hàng theo tên khách hàng");
            System.out.println("6. Thống kê tổng số đơn hàng");
            System.out.println("7. Thống kê tổng doanh thu các đơn hàng Delivered");
            System.out.println("8. Thống kê số lượng đơn hàng theo từng trạng thái");
            System.out.println("9. Tìm kiếm đơn hàng có giá trị lớn nhất");
            System.out.println("0. Thoát");
            System.out.println("**************************************************");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    OrderBusiness.addOrder(scanner);
                    break;
                case 2:
                    OrderBusiness.displayOrders();
                    break;
                case 3:
                    OrderBusiness.updateOrderStatus(scanner);
                    break;
                case 4:
                    OrderBusiness.deleteOrder(scanner);
                    break;
                case 5:
                    OrderBusiness.searchByCustomerName(scanner);
                    break;
                case 6:
                    OrderBusiness.statsTotalOrders();
                    break;
                case 7:
                    OrderBusiness.statsRevenueDelivered();
                    break;
                case 8:
                    OrderBusiness.statsCountByStatus();
                    break;
                case 9:
                    OrderBusiness.findMaxOrder();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default:
                    System.err.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        } while (choice != 0);
    }
}
