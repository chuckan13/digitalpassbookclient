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
public class PassTest {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://stark-castle-00086.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final PassService service = retrofit.create(PassService.class);

    private int create(Pass newItem, Boolean print) {
        Call<Pass> createCall = service.create(newItem);
        try {
            Response<Pass> resp = createCall.execute();
            if(resp.isSuccessful()) {
                Pass item = resp.body();
                if (print) System.out.println("CREATING: name = "+newItem.passName+", id = "+newItem.id+", clubID = "+newItem.organizationid+", userID = "+newItem.userid+", eventID = "+newItem.eventsid);
                if (print) System.out.println("CREATED: name = "+item.passName+", id = "+item.id+", clubID = "+item.organizationid+", userID = "+item.userid+", eventID = "+item.eventsid);
                return item.id;
            } else {
                System.out.println("ERROR - CREATE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - CREATE: "+e);
        }
        return -1;
    }

    private int[] getAll(Boolean print) {
        Call<List<Pass>> callItems = service.getall();
        try {
            Response<List<Pass>> resp = callItems.execute();
            if(resp.isSuccessful()) {
                List<Pass> items = resp.body();
                int[] ids = new int[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    Pass item = items.get(i);
                    ids[i] = item.id;
                    if (print) System.out.println("GETALL: name = "+item.passName+", id = "+item.id);
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
        Call<Pass> deleteItem = service.delete(id);
        try {
            Response<Pass> resp = deleteItem.execute();
            if(resp.isSuccessful()) {
                Pass item = resp.body();
                if (print) System.out.println("DELETED: name = "+item.passName+", id = "+item.id);
                return item.id;
            } else {
                System.out.println("ERROR - DELETE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - DELETE: "+e);
        }
        return -1;
    }

    private int update(int id, Pass org, Boolean print) {
        Call<Pass> updateItem = service.update(id, org);
        try {
            Response<Pass> resp = updateItem.execute();
            if(resp.isSuccessful()) {
                Pass item = resp.body();
                if (print) System.out.println("UPDATE: name = "+item.passName+", id = "+item.id);
                return item.id;
            } else {
                System.out.println("ERROR - UPDATE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return -1;
    }

    private Pass get(int id) {
        Call<Pass> getItem = service.get(id);
        try {
            Response<Pass> resp = getItem.execute();
            if(resp.isSuccessful()) {
                Pass item = resp.body();
                return item;
            } else {
                System.out.println("ERROR - GET: " + resp.errorBody().string());
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR - GET: "+e);
        }
        return new Pass(-1, -1,-1,"");
    }

    private Boolean areEqual(Pass item1, Pass item2, Boolean print) {
        if (print) {
            System.out.println("Differing clubID: " + item1.organizationid + "  " + item2.organizationid);
            System.out.println("Differing userID: " + item1.userid + "  " + item2.userid);
            System.out.println("Differing eventID: " + item1.eventsid + "  " + item2.eventsid);
            System.out.println("Differing passName: " + item1.passName + "  " + item2.passName);
        }
        if (item1.organizationid != item2.organizationid) return false;
        if (item1.userid != item2.userid) return false;
        if (item1.eventsid != item2.eventsid) return false;
        if (!item1.passName.equals(item2.passName)) return false;
        return true;
    }


    @Test
    public void checkPass() {
        Boolean print = true;
        int newClubID = 9;
        int newUserID = 10;
        int newEventID = 5;
        String newPassName = "event111";
        int updatedClubID = 9;
        int updatedUserID = 8;
        int updatedEventID = 7;
        String updatedPassName = "event222";
        Pass newPass = new Pass(newClubID, newUserID, newEventID, newPassName);
        Pass updatedPass = new Pass(updatedClubID, updatedUserID, updatedEventID, updatedPassName);


        int[] ids = getAll(print);
        if (ids.length > 0) assert(ids[0] >= 0);
        int originalLen = ids.length;

        int id = create(newPass, print);
        assert(id >= 0);
        assertEquals(getAll(print).length-1, originalLen);
        assert(areEqual(get(id), newPass, print));

        id = update(id, updatedPass, print);
        assert(id >= 0);
        assert(areEqual(get(id), updatedPass, print));

        id = delete(id, print);
        assert(id >= 0);
        assertEquals(getAll(false).length, originalLen);
    }
}