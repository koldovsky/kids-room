package ua.softserveinc.tc.util;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.schema.impl.XSAnyImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ADFSParser {

    public static Map<String, String> parseCredentials(List<Attribute> attributes) {
        Map<String, String> credentials = new HashMap<>();

        attributes.forEach(a -> {

            if (a.getAttributeValues().get(0) instanceof XSAnyImpl) {
                XSAnyImpl temp = (XSAnyImpl) a.getAttributeValues().get(0);
                String text = temp.getTextContent();
                if (text.contains(" ")) {
                    String[] name = text.split("\\s+", 2);
                    credentials.putIfAbsent("firstName", name[0]);
                    credentials.putIfAbsent("lastName", name[1]);
                } else {
                    credentials.putIfAbsent(a.getName()
                            .substring(a.getName().lastIndexOf('/') + 1), text);
                }
            }
        });

        return credentials;
    }
}
