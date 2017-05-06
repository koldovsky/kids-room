create or replace VIEW timeline
as select bookings.booking_start_time as timeB, bookings.id_room as roomId from bookings
  where booking_start_time > now()
   union SELECT bookings.booking_end_time as timeB, bookings.id_room as roomId from bookings
where booking_end_time > now()
   order by timeB;

create or replace VIEW time_periods
as select
     roomId as periodRoomId,
     timeB as begin,
     (select min(timeB) from timeline as t1 where t1.timeB > begin and t1.roomId = periodRoomId) as end
   from kids.timeline
   GROUP BY begin;
