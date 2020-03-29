package com.example.digitalpassbook;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StudentTest {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pure-river-68629.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final StudentService service = retrofit.create(StudentService.class);

    private int create(Student newItem, Boolean print) {
        Call<Student> createCall = service.create(newItem);
        try {
            Response<Student> resp = createCall.execute();
            if(resp.isSuccessful()) {
                Student item = resp.body();
                if (print) System.out.println("CREATING: name = "+newItem.getName()+", id = "+newItem.getId()+", netid = "+newItem.getNetid());
                if (print) System.out.println("CREATED: name = "+item.getName()+", id = "+item.getId()+", netid = "+newItem.getNetid());
                return item.getId();
            } else {
                System.out.println("ERROR - CREATE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - CREATE: "+e);
        }
        return -1;
    }

    private int[] getAll(Boolean print) {
        Call<List<Student>> callItems = service.getall();
        try {
            Response<List<Student>> resp = callItems.execute();
            if(resp.isSuccessful()) {
                List<Student> items = resp.body();
                int[] ids = new int[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    Student item = items.get(i);
                    ids[i] = item.getId();
                    if (print) System.out.println("GETALL: name = "+item.getName()+", id = "+item.getId());
                }
                return ids;
            } else {
                System.out.println("ERROR - GETALL: " + resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - GETALL: " + e);
        }
        int[] ids = {-1};
        return ids;
    }

    private int delete(int id, Boolean print) {
        Call<Student> deleteItem = service.delete(id);
        try {
            Response<Student> resp = deleteItem.execute();
            if(resp.isSuccessful()) {
                Student item = resp.body();
                if (print) System.out.println("DELETED: name = "+item.getName()+", id = "+item.getId());
                return item.getId();
            } else {
                System.out.println("ERROR - DELETE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - DELETE: "+e);
        }
        return -1;
    }

    private int update(int id, Student org, Boolean print) {
        Call<Student> updateItem = service.update(id, org);
        try {
            Response<Student> resp = updateItem.execute();
            if(resp.isSuccessful()) {
                Student item = resp.body();
                if (print) System.out.println("UPDATE: name = "+item.getName()+", id = "+item.getId());
                return item.getId();
            } else {
                System.out.println("ERROR - UPDATE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return -1;
    }

    private Student get(int id) {
        Call<Student> getItem = service.get(id);
        try {
            Response<Student> resp = getItem.execute();
            if(resp.isSuccessful()) {
                Student item = resp.body();
                return item;
            } else {
                System.out.println("ERROR - GET: " + resp.errorBody().string());
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR - GET: "+e);
        }
        return new Student("", "");
    }

    private Boolean areEqual(Student item1, Student item2, Boolean print) {
        if (print) {
            System.out.println("Differing fullName: " + item1.getName() + "  " + item2.getName());
            System.out.println("Differing netid: " + item1.getNetid() + "  " + item2.getNetid());
        }
        if (!item1.getName().equals(item2.getName())) return false;
        if (!item1.getNetid().equals(item2.getNetid())) return false;
        return true;
    }


//    @Test
    public void checkStudent() {
        Boolean print = false;
        String newName = "David Basili";
        String newNetId = "dbasili";
        String updatedName = "Dane Jacobson";
        String updatedNetId = "danej";
        Student newStudent = new Student(newName, newNetId);
        Student updatedStudent = new Student(updatedName, updatedNetId);


        int[] ids = getAll(print);
        if (ids.length > 0) assert(ids[0] >= 0);
        int originalLen = ids.length;

        int id = create(newStudent, print);
        assert(id >= 0);
        assertEquals(getAll(print).length-1, originalLen);
        assert(areEqual(get(id), newStudent, print));

        id = update(id, updatedStudent, print);
        assert(id >= 0);
        assert(areEqual(get(id), updatedStudent, print));

//        id = delete(id, print);
//        assert(id >= 0);
//        assertEquals(getAll(false).length, originalLen);
    }
}