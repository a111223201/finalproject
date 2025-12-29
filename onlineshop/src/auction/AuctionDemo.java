package auction;

import auction.users.*;
import auction.listings.*;
import auction.bidding.*;
import auction.core.*;
import auction.interfaces.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AuctionDemo {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("============== 2025 線上拍賣全功能展示系統 (No-com 版) ==============");

        // 1. 初始化基礎角色
        Seller seller = new Seller("S01", "王小明 (專業賣家)");
        Buyer bob = new Buyer("B01", "Bob (手動買家)");
        Buyer alice = new Buyer("B02", "Alice (系統競爭者)");
        Administrator admin = new Administrator("A01", "系統管理員");

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- 請選擇要展示的功能場景 ---");
            System.out.println("1) [一般競標] 手動 vs 自動出價 + 時間延長 + 關注通知");
            System.out.println("2) [荷蘭式拍賣] 模擬價格隨時間遞減");
            System.out.println("3) [底價拍賣] 測試未達底價流標邏輯");
            System.out.println("4) [直購商品] 固定價格立即購買");
            System.out.println("5) [售後流程] 付款、運送、評價");
            System.out.println("6) [爭議處理] 管理員介入模擬");
            System.out.println("0) 退出系統");
            System.out.print("請輸入選擇: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1: showStandardScenario(seller, bob, alice); break;
                case 2: showDutchScenario(seller, bob); break;
                case 3: showReserveScenario(seller, bob); break;
                case 4: showBuyNowScenario(seller, bob); break;
                case 5: showPostAuctionScenario(seller, bob); break;
                case 6: showDisputeScenario(admin); break;
                case 0: exit = true; break;
                default: System.out.println("無效選擇！");
            }
        }
    }

    // --- 1. 一般競標與自動競爭 ---
    private static void showStandardScenario(Seller seller, Buyer bob, Buyer alice) {
        System.out.println("\n>>> 場景 1: 一般競標展示 (支援自動加價與時間延長)");
        StandardAuction auction = new StandardAuction("ST-01", seller, "iPhone 16 Pro", 30000, 1000, LocalDateTime.now().plusMinutes(2));

        auction.addWatcher(bob);
        auction.addWatcher(alice);

        System.out.print("請設定 Alice (自動出價者) 的最高上限: ");
        double limit = scanner.nextDouble();
        auction.setAutoBid(new AutoBid(alice, limit));

        System.out.print("您 (Bob) 的出價金額: ");
        double bobBid = scanner.nextDouble();

        try {
            BidResult res = auction.placeBid(bob, bobBid);
            System.out.println(res);
            System.out.println("目前最高出價者: " + auction.getCurrentHighestBid().getBidder().username);
            System.out.println("最高金額: $" + auction.getCurrentHighestBid().getAmount());
        } catch (Exception e) {
            System.out.println("[錯誤] " + e.getMessage());
        }
    }

    // --- 2. 荷蘭式拍賣 ---
    private static void showDutchScenario(Seller seller, Buyer bob) {
        System.out.println("\n>>> 場景 2: 荷蘭式拍賣 (價格遞減)");
        DutchAuction dutch = new DutchAuction("DT-01", seller, "限定版咖啡機", 10000, 5000, LocalDateTime.now().plusSeconds(30));

        System.out.println("起始價格: 10000");
        System.out.println("當前動態價格: " + dutch.getCurrentPrice());
        System.out.print("是否以此價格立即購買? (y/n): ");
        if (scanner.next().equalsIgnoreCase("y")) {
            try {
                dutch.placeBid(bob, dutch.getCurrentPrice());
                System.out.println("成功！成交價: " + dutch.getCurrentHighestBid().getAmount());
            } catch (Exception e) { System.out.println(e.getMessage()); }
        }
    }

    // --- 3. 底價拍賣 ---
    private static void showReserveScenario(Seller seller, Buyer bob) {
        System.out.println("\n>>> 場景 3: 底價拍賣測試");
        double reserve = 5000;
        ReserveAuction resAuction = new ReserveAuction("RS-01", seller, "古董花瓶", 1000, 500, reserve, LocalDateTime.now().plusSeconds(5));

        System.out.println("商品底價為: " + reserve);
        System.out.print("您的出價: ");
        double bid = scanner.nextDouble();
        resAuction.placeBid(bob, bid);

        System.out.println("正在檢查成交結果 (模擬結束)...");
        if (resAuction.isSuccessfullySold()) {
            System.out.println("【成功】已達底價並成交！");
        } else {
            System.out.println("【流標】未達底價 " + reserve + "。");
        }
    }

    // --- 4. 直購商品 ---
    private static void showBuyNowScenario(Seller seller, Buyer bob) {
        System.out.println("\n>>> 場景 4: 直購商品展示");
        BuyNowListing buyNow = new BuyNowListing("BN-01", seller, "全新鍵盤", 3000, LocalDateTime.now().plusDays(1));
        System.out.println("直購價格: 3000");
        System.out.print("請輸入購買金額確認: ");
        double amt = scanner.nextDouble();
        try {
            System.out.println(buyNow.placeBid(bob, amt));
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    // --- 5. 完整售後流程 ---
    private static void showPostAuctionScenario(Seller seller, Buyer bob) {
        System.out.println("\n>>> 場景 5: 售後流程 (付款/運送/評價)");
        ManualBid winBid = new ManualBid(bob, 15000, LocalDateTime.now());
        Transaction tx = new Transaction("TX-999", winBid);

        Payment pay = new Payment("信用卡", 15000);
        pay.confirm();
        tx.setPayment(pay);
        System.out.println("1. 付款成功！");

        ShippingInfo ship = new ShippingInfo("台北市...");
        ship.updateStatus("已出貨", "SF-12345");
        tx.setShipping(ship);
        System.out.println("2. 物流已發出！");

        System.out.print("3. 給予賣家評分 (1-5): ");
        int score = scanner.nextInt();
        scanner.nextLine();
        seller.addRating(bob, score, "優質賣家！");
        System.out.printf("賣家平均分數更新為: %.1f 星\n", seller.getAverageRating());
    }

    // --- 6. 爭議處理 ---
    private static void showDisputeScenario(Administrator admin) {
        System.out.println("\n>>> 場景 6: 爭議處理展示");
        Dispute dispute = new Dispute("買家檢舉商品敘述不符");
        System.out.println("收到申訴: " + "買家檢舉商品敘述不符");
        System.out.print("管理員介入，請輸入裁決結果: ");
        String res = scanner.nextLine();
        admin.resolveDispute(dispute, res);
        System.out.println("爭議已結案。");
    }
}