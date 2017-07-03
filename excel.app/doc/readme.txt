Table 1: TradeMe Shipment
Table 2: FastWay Bag
Table 3: Fastway Shipment

Table 3 Query:
SELECT t.CUSTOMER_REFERENCE AS Reference,
       t.ORDER_NOTES_PUBLIC,
       t. CUSTOMER,
       t.ADDRESS1,
       t.ADDRESS2,
       t.ADDRESS3,
       t.TOWN_CITY,
       t.POST_CODE,
       t.CUSTOMER_EMAIL,
       t.PHONE_NUMBER,
       CONCAT(f.BARCODE, '_____', f.LOCATION) AS BARCODE_LOCATION,
       CONCAT(t.QTY_REQUESTED, '_____', t.UNIT_PRICE_INC_TAX) AS QTY_AND_UNIT_PRICE_INC,
       t.PRODUCT_NAME,
       f.BAG,
       t.PRODUCT_ALTERNATE_CODE,
       '1' AS QTY_REQUESTED,
       t.LINE_TOTAL_INC_TAX,
       t.NOTES,
       f.BARCODE
FROM TRADE_ME t,
     FASTWAY_BAGS f
WHERE t.PRODUCT_ALTERNATE_CODE = f.SKU
