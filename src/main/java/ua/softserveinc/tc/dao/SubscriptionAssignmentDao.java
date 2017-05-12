package ua.softserveinc.tc.dao;

import ua.softserveinc.tc.dto.SubscriptionsUsedHoursDto;
import ua.softserveinc.tc.dto.UserAssigmentDto;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.entity.pagination.SortingPagination;

import java.util.List;
import java.util.Map;

public interface SubscriptionAssignmentDao extends BaseDao<SubscriptionAssignment> {

    List<SubscriptionsUsedHoursDto> getAssignmentByUserId(long userId);
}
