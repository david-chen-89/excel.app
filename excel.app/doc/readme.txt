Table 1: TradeMe Shipment
Table 2: FastWay Bag
Table 3: Fastway Shipment

Table 3 Query:
SELECT
  t.customer_reference AS Reference,
  t.order_notes_public,
  t.customer,
  t.address1,
  t.address2,
  t.address3,
  t.town_city,
  t.post_code,
  t.customer_email,
  t.phone_number,
  CASE
    WHEN f.barcode IS NOT NULL THEN Concat(f.barcode, '_____', f.location)
    ELSE ''
  END AS BARCODE_LOCATION,
  CASE
    WHEN t.qty_requested IS NOT NULL THEN Concat(t.qty_requested, '_____', t.unit_price_inc_tax)
    ELSE ''
  END AS QTY_AND_UNIT_PRICE_INC,
  t.product_name,
  f.bag,
  t.product_alternate_code,
  '1' AS QTY_REQUESTED,
  t.line_total_inc_tax,
  t.notes,
  f.barcode
FROM trade_me t
LEFT JOIN fastway_bags f
  ON t.product_alternate_code = f.sku