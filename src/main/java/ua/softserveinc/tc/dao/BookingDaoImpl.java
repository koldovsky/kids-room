package ua.softserveinc.tc.dao;

import org.springframework.stereotype.Repository;

import ua.softserveinc.tc.entity.Booking;

import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * Created by TARAS on 01.05.2016.
 */
@Repository
public class BookingDaoImpl extends BaseDaoImpl<Booking> implements BookingDao{
}
