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
public class EventTest {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://stark-castle-00086.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private final EventService service = retrofit.create(EventService.class);

    private int create(Event newItem, Boolean print) {
        Call<Event> createCall = service.create(newItem);
        try {
            Response<Event> resp = createCall.execute();
            if(resp.isSuccessful()) {
                Event item = resp.body();
                if (print) System.out.println("CREATING: name = "+newItem.eventName+", id = "+newItem.id+", orgid = "+newItem.orgId);
                if (print) System.out.println("CREATED: name = "+item.eventName+", id = "+item.id+", orgid = "+item.orgId);
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
        Call<List<Event>> callItems = service.getall();
        try {
            Response<List<Event>> resp = callItems.execute();
            if(resp.isSuccessful()) {
                List<Event> items = resp.body();
                int[] ids = new int[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    Event item = items.get(i);
                    ids[i] = item.id;
                    if (print) System.out.println("GETALL: name = "+item.eventName+", id = "+item.id);
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
        Call<Event> deleteItem = service.delete(id);
        try {
            Response<Event> resp = deleteItem.execute();
            if(resp.isSuccessful()) {
                Event item = resp.body();
                if (print) System.out.println("DELETED: name = "+item.eventName+", id = "+item.id);
                return item.id;
            } else {
                System.out.println("ERROR - DELETE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR - DELETE: "+e);
        }
        return -1;
    }

    private int update(int id, Event org, Boolean print) {
        Call<Event> updateItem = service.update(id, org);
        try {
            Response<Event> resp = updateItem.execute();
            if(resp.isSuccessful()) {
                Event item = resp.body();
                if (print) System.out.println("UPDATE: name = "+item.eventName+", id = "+item.id);
                return item.id;
            } else {
                System.out.println("ERROR - UPDATE: "+resp.errorBody().string());
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return -1;
    }

    private Event get(int id) {
        Call<Event> getItem = service.get(id);
        try {
            Response<Event> resp = getItem.execute();
            if(resp.isSuccessful()) {
                Event item = resp.body();
                return item;
            } else {
                System.out.println("ERROR - GET: " + resp.errorBody().string());
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR - GET: "+e);
        }
        return new Event(-1, "");
    }

    private Boolean areEqual(Event item1, Event item2, Boolean print) {
        if (print) {
            System.out.println("Differing orgids: " + item1.orgId + "  " + item2.orgId);
            System.out.println("Differing eventNames: " + item1.eventName + "  " + item2.eventName);
            System.out.println("Differing descriptions: " + item1.description + "  " + item2.description);
            System.out.println("Differing dates: " + item1.date + "  " + item2.date);
            System.out.println("Differing startTimes: " + item1.startTime + "  " + item2.startTime);
            System.out.println("Differing endTimes: " + item1.endTime + "  " + item2.endTime);
            System.out.println("Differing locations: " + item1.location + "  " + item2.location);
        }
        if (item1.orgId != item2.orgId) return false;
        if (!item1.eventName.equals(item2.eventName)) return false;
        if (!item1.description.equals(item2.description)) return false;
        if (!item1.date.equals(item2.date)) return false;
        if (!item1.startTime.equals(item2.startTime)) return false;
        if (!item1.endTime.equals(item2.endTime)) return false;
        if (!item1.location.equals(item2.location)) return false;
        return true;
    }


    @Test
    public void checkEvent() {
        Boolean print = false;
        int orgID = 25;
        String newName = "event 1";
        String updatedName = "event 2";
        Event newEvent = new Event(orgID, newName);
        Event updatedEvent = new Event(orgID, updatedName);


        int[] ids = getAll(print);
        if (ids.length > 0) assert(ids[0] >= 0);
        int originalLen = ids.length;

        int id = create(newEvent, print);
        assert(id >= 0);
        assertEquals(getAll(print).length-1, originalLen);
        assert(areEqual(get(id), newEvent, print));

        id = update(id, updatedEvent, print);
        assert(id >= 0);
        assert(areEqual(get(id), updatedEvent, print));

        id = delete(id, print);
        assert(id >= 0);
        assertEquals(getAll(false).length, originalLen);



    }
}