package ua.softserveinc.tc.util;

public class AbonnementCharacteristics {
    public int defineAbonnementsPage(int start, int paginationLength, long allAbonnementsLength) {
        int totalPages = (int) allAbonnementsLength / paginationLength;
        return (int) (totalPages * start / allAbonnementsLength);
    }
}
