package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.AbonnementUsageDao;
import ua.softserveinc.tc.dao.SubscriptionAssignmentDao;
import ua.softserveinc.tc.dto.SubscriptionAssignmentDto;
import ua.softserveinc.tc.entity.AbonnementUsage;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.service.AbonnementUsageService;

import java.util.List;

@Service
public class AbonnementUsageServiceImpl extends BaseServiceImpl<AbonnementUsage> implements AbonnementUsageService {

    @Autowired
    private AbonnementUsageDao abonnementUsageDao;

    @Autowired
    private SubscriptionAssignmentDao subscriptionAssignmentDao;

    @Override
    public List<AbonnementUsage> findAllUsagesByUserId(long userId) {
        return null;
    }

    @Override
    public void updateSubscription(SubscriptionAssignment assignment) {
        subscriptionAssignmentDao.update(assignment);
    }
}
