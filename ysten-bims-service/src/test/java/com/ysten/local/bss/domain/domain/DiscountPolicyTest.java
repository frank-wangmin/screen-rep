package com.ysten.local.bss.domain.domain;

public class DiscountPolicyTest {
	/*
    
    @Test
    public void testAddUp() {
        DiscountPolicyGroup discountPolicyGroup = createPolicyGroup(OperateType.ADDUP);
        Assert.assertEquals(discountPolicyGroup.getDiscountPrice(new BasePrice(1000L,0.9), null).getPayPrice().intValue(),50);
        
    }

    @Test
    public void testMax() {
        DiscountPolicyGroup discountPolicyGroup = createPolicyGroup(OperateType.MAX);
        Assert.assertEquals(discountPolicyGroup.getDiscountPrice(new BasePrice(1000L,0.9), null).getPayPrice().intValue(), 450);
        
    }
    @Test
    public void testMin() {
        DiscountPolicyGroup discountPolicyGroup = createPolicyGroup(OperateType.MIN);
        Assert.assertEquals(discountPolicyGroup.getDiscountPrice(new BasePrice(1000L,0.9), null).getPayPrice().intValue(), 100);
        
    }

    @Test
    public void testPrice() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-05-01 00:00:00");
        dateDefine.setCheckPar2("2011-05-04 23:59:59");
        dateDefine.setCheckType(CheckType.DATETIME);
        
        dateDefine.setDiscountType(DiscountType.PRICE);
        dateDefine.setDiscountPar1("50");
        
        Date cDate = parDateTime("2011-05-01 23:00:00");
        BasePrice price1 = new BasePrice(100L, 0.8);
        BasePrice price2 = dateDefine.getDiscountPrice(price1, cDate);
        Assert.assertEquals(price2.getDiscount(),0.8);
        Assert.assertEquals(price2.getPrice().intValue(), 100 - 50);
    }
    
    @Test
    public void testDiscount() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-05-01 00:00:00");
        dateDefine.setCheckPar2("2011-05-04 23:59:59");
        dateDefine.setCheckType(CheckType.DATETIME);
        
        dateDefine.setDiscountType(DiscountType.DISCOUNT);
        dateDefine.setDiscountPar1("0.5");
        
        Date cDate = parDateTime("2011-05-01 23:00:00");
        BasePrice price1 = new BasePrice(1L, 0.8);
        BasePrice price2 = dateDefine.getDiscountPrice(price1, cDate);
        Assert.assertEquals(price2.getDiscount(),(0.8 * 0.5));
        Assert.assertEquals(price2.getPrice().intValue(),1);
    }
    
    @Test
    public void testResetPrice() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-05-01 00:00:00");
        dateDefine.setCheckPar2("2011-05-04 23:59:59");
        dateDefine.setCheckType(CheckType.DATETIME);
        
        dateDefine.setDiscountType(DiscountType.RESETPRICE);
        dateDefine.setDiscountPar1("1000");
        
        Date cDate = parDateTime("2011-05-01 23:00:00");
        BasePrice price1 = new BasePrice(1L, 0.8);
        BasePrice price2 = dateDefine.getDiscountPrice(price1, cDate);
        Assert.assertEquals(price2.getDiscount(),0.8);
        Assert.assertEquals(price2.getPrice().intValue() , 1000);
    }

    @Test
    public void testResetDiscount() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-05-01 00:00:00");
        dateDefine.setCheckPar2("2011-05-04 23:59:59");
        dateDefine.setCheckType(CheckType.DATETIME);
        
        dateDefine.setDiscountType(DiscountType.RESETDISCOUNT);
        dateDefine.setDiscountPar1("0.9");
        
        Date cDate = parDateTime("2011-05-01 23:00:00");
        BasePrice price1 = new BasePrice(1L, 0.8);
        BasePrice price2 = dateDefine.getDiscountPrice(price1, cDate);
        Assert.assertEquals(price2.getPrice().intValue() , 1);
        Assert.assertEquals(price2.getDiscount(),(0.9));
    }

    @Test
    public void testDateTimeType() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-05-01 00:00:00");
        dateDefine.setCheckPar2("2011-05-04 23:59:59");
        dateDefine.setCheckType(CheckType.DATETIME);
        Assert.assertTrue(dateDefine.isValid(parDateTime("2011-05-01 23:00:00")));
        Assert.assertFalse(dateDefine.isValid(parDateTime("2011-04-01 00:00:00")));
        Assert.assertFalse(dateDefine.isValid(parDateTime("2011-05-06 00:00:00")));  
        
    }

    @Test
    public void testTimeType() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("18:00:00");
        dateDefine.setCheckPar2("20:00:00");
        dateDefine.setCheckType(CheckType.TIME);
        Assert.assertTrue(dateDefine.isValid(parTime("18:30:00")));
        Assert.assertFalse(dateDefine.isValid(parTime("16:00:00")));
        Assert.assertFalse(dateDefine.isValid(parTime("21:00:00"))); 
        
    }

    @Test
    public void testDateType() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckPar1("2011-01-01");
        dateDefine.setCheckPar2("2011-01-30");
        dateDefine.setCheckType(CheckType.DATE);
        Assert.assertTrue(dateDefine.isValid(parDate("2011-01-05")));
        Assert.assertFalse(dateDefine.isValid(parDate("2011-02-05")));
        Assert.assertFalse(dateDefine.isValid(parDate("2010-01-05")));   
    }
    
    @Test
    public void testNone() {
        ProductDiscountPolicyDefine dateDefine = new ProductDiscountPolicyDefine();
        dateDefine.setCheckType(CheckType.NONE);
        Assert.assertTrue(dateDefine.isValid(parDate("2011-01-05")));
    }
    
    private DiscountPolicyGroup createPolicyGroup(OperateType policyType) {
        DiscountPolicyGroup policyGroup = new DiscountPolicyGroup();
        policyGroup.setOperateType(policyType);
        
        ProductDiscountPolicyDefine policyDefine1 = new ProductDiscountPolicyDefine();
        policyDefine1.setCheckType(CheckType.NONE);
        policyDefine1.setDiscountType(DiscountType.RESETPRICE);
        policyDefine1.setDiscountPar1("500");
        
        
        ProductDiscountPolicyDefine policyDefine2 = new ProductDiscountPolicyDefine();
        policyDefine2.setCheckType(CheckType.NONE);
        policyDefine2.setDiscountType(DiscountType.RESETDISCOUNT);
        policyDefine2.setDiscountPar1("0.1");
        
        policyGroup.addDiscountPolicy(policyDefine1);
        policyGroup.addDiscountPolicy(policyDefine2);
        return policyGroup;
    }
    
    private static Date parDate(String source){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Date parTime(String source){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Date parDateTime(String source){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
   */
}
