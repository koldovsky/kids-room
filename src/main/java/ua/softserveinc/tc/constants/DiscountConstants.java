package ua.softserveinc.tc.constants;

/**
 * Created by Tat0 on 15.03.2017.
 */
public final class DiscountConstants {

  private DiscountConstants() {

  }

  public static final class Entity {
    public static final String TABLE_NAME_DAYSDISCOUNTS = "day_discounts";
    public static final String TABLE_NAME_PERSONALDISCOUNTS = "personal_discounts";
    public static final String DAY_DISCOUNT_ID = "id";
    public static final String DISCOUNT_REASON = "reason";
    public static final String DISCOUNT_VALUE = "value";
    public static final String DISCOUNT_START_DATE = "start_date";
    public static final String DISCOUNT_START_TIME = "start_time";
    public static final String DISCOUNT_END_TIME = "end_time";
    public static final String DISCOUNT_END_DATE = "end_date";
    public static final String DISCOUNT_ACTIVE = "isActive";
  }

}
