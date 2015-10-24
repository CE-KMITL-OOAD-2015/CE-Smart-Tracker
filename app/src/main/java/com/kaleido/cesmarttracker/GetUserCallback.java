package com.kaleido.cesmarttracker;

import com.kaleido.cesmarttracker.data.User;

/**
 * Created by pirushprechathavanich on 9/9/15 AD.
 */
interface GetUserCallback {

    public abstract void done(User returnedUser);

}
