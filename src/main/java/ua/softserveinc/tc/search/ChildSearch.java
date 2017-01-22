package ua.softserveinc.tc.search;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.SearchConstants;
import ua.softserveinc.tc.entity.Child;


@Repository
public class ChildSearch extends BaseSearch<Child> {

    public ChildSearch() {
        searchFields = SearchConstants.getChildSearchFields();
    }

    @Override
    public Class<Child> getTClass() {
        return Child.class;
    }

}
