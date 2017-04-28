package sorenkt.class2017.kea.space_game;

import java.util.ArrayList;
import java.util.List;

public abstract class Pool<T>
{
    private List<T> items = new ArrayList<>();

    protected abstract T newItem();

    public T obtain()
    {
        int size = items.size();
        if(size == 0) return newItem(); //if list is emtry, create a new item
        return items.remove(size - 1); // else remove
    }

    public void free(T item)
    {
        items.add(item);
    }
}
