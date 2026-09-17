import java.util.Objects;

public class Pair <T,U>{
    T t;
    U u;
    public Pair(T t, U u){
        this.t=t;
        this.u=u;
    }

    public T getFront(){
        return t;
    }

    public U getBack(){
        return u;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return Objects.equals(t, pair.t) && Objects.equals(u, pair.u);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, u);
    }
}
