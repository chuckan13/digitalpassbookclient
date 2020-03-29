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
public class OrganizationTest {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pure-river-68629.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final OrganizationService service = retrofit.create(OrganizationService.class);

    private int create(String name, Boolean print) {
        Organization createItem = new Organization(name);
        Call<Organization> createCall = service.create(createItem);
        try {
            Response<Organization> resp = createCall.execute();
            if(resp.isSuccessful()) {
                Organization item = resp.body();
                if (print) System.out.println("CREATE: name = "+item.getName()+", id = "+item.getId());
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
        Call<List<Organization>> callItems = service.getall();
        try {
            Response<List<Organization>> resp = callItems.execute();
            if(resp.isSuccessful()) {
                List<Organization> items = resp.body();
                int[] ids = new int[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    Organization item = items.get(i);
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
        Call<Organization> deleteItem = service.delete(id);
        try {
            Response<Organization> resp = deleteItem.execute();
            if(resp.isSuccessful()) {
                Organization item = resp.body();
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

    private int update(int id, Organization org, Boolean print) {
        Call<Organization> updateItem = service.update(id, org);
        try {
            Response<Organization> resp = updateItem.execute();
            if(resp.isSuccessful()) {
                Organization item = resp.body();
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

    private String getName(int id) {
        Call<Organization> getItem = service.get(id);
        try {
            Response<Organization> resp = getItem.execute();
            if(resp.isSuccessful()) {
                Organization item = resp.body();
                return item.getName();
            } else {
                System.out.println("ERROR - GET: " + resp.errorBody().string());
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR - GET: "+e);
        }
        return "";
    }


//    @Test
    public void checkOrganization() {
        Boolean print = false;

        int[] ids = getAll(print);
        assert(ids[0] >= 0);
        int originalLen = ids.length;

        String newName = "TI";
        int id = create(newName, print);
        assert(id >= 0);
        assertEquals(getAll(print).length-1, originalLen);
        assertEquals(getName(id), newName);

        newName = "Ivy";
        Organization updatedOrg = new Organization(newName);
        id = update(id, updatedOrg, print);
        assert(id >= 0);
        assertEquals(getName(id), newName);

        int deleteid = delete(id, print);
        assert(deleteid >= 0);
        assertEquals(getAll(false).length, originalLen);
    }
}