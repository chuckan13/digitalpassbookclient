package com.example.digitalpassbook;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void check_organization() {
        System.out.println("TESTING NOW!");
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://stark-castle-00086.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        final OrganizationService service = retrofit.create(OrganizationService.class);

        /*
        Organization organization = new Organization("David");
        Call<Organization> createCall = service.create(organization);
        try {
            Response<Organization> resp = createCall.execute();
            Organization newItem = resp.body();
            System.out.println("CREATE: name = "+newItem.name+", id = "+newItem.id);
            assertEquals("David", newItem.name);
        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        }

        Call<List<Organization>> allOrgs = service.getall();
        try {
            Response<List<Organization>> resp = allOrgs.execute();
            List<Organization> newItems = resp.body();
            for (Organization temp : newItems) {
                System.out.println("GETALL: name = "+temp.name+", id = "+temp.id);
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        }

        Call<Organization> org1 = service.get(1);
        try {
            Response<Organization> resp = org1.execute();
            Organization newItem = resp.body();
            System.out.println("GET: name = "+newItem.name+", id = "+newItem.id);
        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        }
*/
        Call<Organization> deleteOrg = service.removeOrganization(5);
        try {
            Response<Organization> resp = deleteOrg.execute();
            if(resp.isSuccessful()) {
                System.out.println("DELETED");
//                Organization newItem = resp.body();
//                System.out.println("DELETE: name = "+newItem.name+", id = "+newItem.id);
            } else {
                System.out.println("ERROR: "+resp.errorBody());
            }

        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        }
        Call<List<Organization>> allOrgs = service.getall();
        try {
            Response<List<Organization>> resp = allOrgs.execute();
            List<Organization> newItems = resp.body();
            for (Organization temp : newItems) {
                System.out.println("GETALL: name = "+temp.name+", id = "+temp.id);
            }
        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        }
/*
        Organization updatedOrganization = new Organization("Basili");
        Call<Organization> changeOrg = service.updateOrganization(6, updatedOrganization);
        try {
            Response<Organization> resp = changeOrg.execute();
            Organization newItem = resp.body();
            System.out.println("name = "+newItem.name+", id = "+newItem.id);
            assertEquals("Basili", newItem.name);
        } catch (java.io.IOException e) {
            System.out.println("ERROR: "+e);
        } catch (RuntimeException e) {
            System.out.println("ERROR: "+e);
        } */
    }


}