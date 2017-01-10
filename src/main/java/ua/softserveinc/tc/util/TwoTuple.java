package ua.softserveinc.tc.util;

/**
 * A classic tuple, i.e. it is simply a group of two objects wrapped together
 * into a single object.
 * If F and S are immutable objects that this tuple is thread safe, otherwise - no!
 * <p>
 * Created by Sviatoslav Hryb on 05-Jan-17.
 */
public class TwoTuple<F,S> {

    private final F first;
    private final S second;

    public TwoTuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TwoTuple)) {
            return false;
        }
        TwoTuple tuple = (TwoTuple) o;

        return this.first.equals(tuple.getFirst()) && this.second.equals(tuple.getSecond());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}
