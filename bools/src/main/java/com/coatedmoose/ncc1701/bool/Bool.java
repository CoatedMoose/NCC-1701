package com.coatedmoose.ncc1701.bool;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Andrew Crichton (andrew@coatedmoose.com)
 */
public final class Bool implements Serializable, Comparable<Bool>, Closeable {
    // TODO: Offer configuration option to pick new id's on every static initialization.
    // IDs of constant booleans.
    private static final String TRUE_BOOLEAN_ID = "a9616b4e-c622-4531-87e8-0444ebac3f1f";
    private static final String FALSE_BOOLEAN_ID = "a95f6faf-dd70-4ac3-ac9b-b724a857d37e";
    private static BooleanIOService service;

    public static final Bool TRUE = getTrue();
    public static final Bool FALSE = getFalse();

    private final String id;

    public Bool() {
        try {
            id = service.createBool().execute().body().id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Bool(String id) {
        this.id = id;
    }

    public Bool(boolean bool) {
        try {
            id = service.createBool(bool).execute().body().id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deep copy constructor.
     *
     * @param bool
     */
    public Bool(Bool bool) {
        try {
            this.id = service.createBool(service.getBool(bool.id).execute().body().val).execute().body().id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void set(boolean val) {
        if (this == TRUE || this == FALSE) {
            throw new IllegalArgumentException("Can't change truthiness.");
        }
        if (this.id.equals(TRUE.id) || this.id.equals(FALSE.id)) {
            throw new IllegalArgumentException(
                    "Can't indirectly change truthiness by mutating based on id instead of instance.");
        }
        try {
            if (!service.updateBool(this.id, val).execute().isSuccessful()) {
                throw new RuntimeException("Updating boolean was not successful.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Updating boolean was not successful.");
        }
    }

    public final void set(Bool val) {
        try {
            set(service.getBool(val.id).execute().body().val);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Bool bool) {
        return compare(this, bool);
    }

    @Override
    public boolean equals(Object obj) {
        //noinspection SimplifiableIfStatement
        if (obj instanceof Bool) {
            return compare(this, (Bool) obj) == 0;
        }
        return false;
    }

    public static int compare(Bool x, Bool y) {
        boolean isEqual = false;
        // If the instances are the same, then the value must be the same.
        if (x == y) {
            isEqual = true;
        }
        // If the IDs are equal, then the value must be the same.
        if (x.id.equals(y.id)) {
            isEqual = true;
        }

        try {
            if (service.getBool(x.id).execute().body().val == service.getBool(y.id).execute().body().val) {
                isEqual = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return isEqual ? 0 : 1;
    }

    private static Bool toBool(String booleanString) {
        return new Bool(booleanString != null && booleanString.equalsIgnoreCase("true"));
    }

    static Bool getTrue() {
        try {
            BooleanIOBoolean body = getService().updateBool(TRUE_BOOLEAN_ID, true).execute().body();
            return new Bool(body.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Bool getFalse() {
        try {
            return new Bool(getService().updateBool(FALSE_BOOLEAN_ID, false).execute().body().id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return equals(TRUE) ? "true" : "false";
    }

    private static BooleanIOService getService() {
        if (service == null) {
            service = new Retrofit.Builder()
                    .baseUrl("https://api.booleans.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BooleanIOService.class);
            if (service == null) {
                throw new NullPointerException("Service is null after attempt to create it.");
            }
        }
        return service;
    }

    @Override
    public void close() throws IOException {
        service.deleteBool(id).execute();
    }
}
