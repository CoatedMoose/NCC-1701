package com.coatedmoose.ncc1701.bool;

import org.junit.Test;

import static com.coatedmoose.ncc1701.bool.Bool.FALSE;
import static com.coatedmoose.ncc1701.bool.Bool.TRUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Andrew Crichton (andrew@coatedmoose.com).
 */
public class BoolTest {

    @Test
    public void testTrueIsNotFalse() throws Exception {
        assertThat(TRUE, not(equalTo(FALSE)));
    }

    @Test
    public void testNewTrue() throws Exception {
        Bool truthy = new Bool(true);
        assertThat(truthy, equalTo(TRUE));
        assertThat(truthy, not(equalTo(FALSE)));
    }

    @Test
    public void testNewFalse() throws Exception {
        Bool falsey = new Bool(false);
        assertThat(falsey, equalTo(FALSE));
        assertThat(falsey, not(equalTo(TRUE)));
    }

    @Test
    public void testTrueIsTrue() throws Exception {
        assertThat(TRUE, equalTo(TRUE));
    }

    @Test
    public void testFalseIsFalse() throws Exception {
        assertThat(FALSE, equalTo(FALSE));
    }

    @Test
    public void testUpdate() throws Exception {
        Bool truthyCopy = new Bool(TRUE);
        assertThat(truthyCopy, equalTo(TRUE));
        truthyCopy.set(FALSE);
        assertThat(truthyCopy, equalTo(FALSE));
    }

    @Test
    public void testConstantUpdate() throws Exception {
        try {
            FALSE.set(true);
            fail("Setting false to true succeeded.");
        } catch (Exception ignored) {
        }
        try {
            TRUE.set(false);
            fail("Setting true to false succeeded.");
        } catch (Exception ignored) {
        }
    }
}