package processing.app.data;

import java.util.List;

public interface DataDao {
    public List<Data> getAllData();

    public String getStringData(String Tag, String Default);

    public int getIntData(String Tag, int Default);

    public boolean getBooleanData(String Tag, Boolean Default);

    public float getFloatData(String Tag, float Default);

    public void loadData(String file);

    public void saveData();

    public void insertData(Data data);

    public void updateData(String tag, String value);

    public void updateData(String tag, Boolean value);

    public void updateData(String tag, int value);

    public void updateData(String tag, float value);

    public void deleteData(String tag);
}
