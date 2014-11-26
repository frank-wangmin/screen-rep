//package com.ysten.iptv.bss.domain.domain;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import com.ysten.iptv.bss.order.domain.OrderItem;
//import com.ysten.iptv.bss.order.domain.ProductOrder;
//import com.ysten.iptv.bss.product.domain.BillCycleType;
//import com.ysten.iptv.bss.product.domain.ProductType;
//
//public class OrderTest {
//    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//    @Test
//    public void orderItemBasicIsValidTest(){
//        OrderItem orderItem = new OrderItem();
//        orderItem.setProductType(ProductType.BASIC);
//        Assert.assertTrue(orderItem.isValid(new Date()));
//    }
//    
//    @Test
//    public void orderItemSingleIsValidTest(){
//        OrderItem item = new OrderItem();
//        item.setProductType(ProductType.SINGLE);
//        item.setStartDate(parseDate("2011-01-01"));
//        item.setEndDate(parseDate("2011-02-10"));
//        Assert.assertTrue(item.isValid(parseDate("2011-02-01")));
//    }
//    
//    @Test
//    public void calculateBillingDateOfNaturalMonthTest(){
//        ProductOrder order = new ProductOrder();
//        order.setOrderDate(parseDate("2010-12-2"));
//        
//        OrderItem orderItem1 = new OrderItem();
//        orderItem1.setBillCycleType(BillCycleType.NATURAL_MONTH);
//        orderItem1.setStartDate(parseDate("2011-01-01"));
//        orderItem1.setOrder(order);
//        
//        Assert.assertEquals("2011-01-01", orderItem1.calculateBillingDate(parseDate("2011-01-01")));
//        Assert.assertEquals("2011-01-01", orderItem1.calculateBillingDate(parseDate("2011-01-11")));
//        Assert.assertEquals("2011-02-01", orderItem1.calculateBillingDate(parseDate("2011-02-01")));
//        Assert.assertEquals("2011-02-01", orderItem1.calculateBillingDate(parseDate("2011-02-21")));
//    }
//    
//    @Test
//    public void calculateBillingDateOfOrderDayTest(){
//        ProductOrder order = new ProductOrder();
//        order.setOrderDate(parseDate("2010-12-2"));
//        
//        OrderItem orderItem1 = new OrderItem();
//        orderItem1.setBillCycleType(BillCycleType.NATURAL_MONTH);
//        orderItem1.setStartDate(parseDate("2011-01-11"));
//        orderItem1.setOrder(order);
//        
//        Assert.assertEquals("2011-01-11", orderItem1.calculateBillingDate(parseDate("2011-01-21")));
//        Assert.assertEquals("2011-01-11", orderItem1.calculateBillingDate(parseDate("2011-02-05")));
//        Assert.assertEquals("2011-02-11", orderItem1.calculateBillingDate(parseDate("2011-02-11")));
//        Assert.assertEquals("2011-02-11", orderItem1.calculateBillingDate(parseDate("2011-03-01")));
//    }
//    
//    private Date parseDate(String src){
//        try {
//            return DATE_FORMAT.parse(src);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
