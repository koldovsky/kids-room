package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.AbonnementUsage;
import ua.softserveinc.tc.entity.SubscriptionAssignment;

import java.util.List;

public interface AbonnementUsageService extends BaseService<AbonnementUsage> {

    List<AbonnementUsage> findAllUsagesByUserId(long userId);

    void updateSubscription(SubscriptionAssignment assignment);
}
