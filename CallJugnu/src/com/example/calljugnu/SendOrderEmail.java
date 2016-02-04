package com.example.calljugnu;

import android.os.AsyncTask;
import android.util.Log;

public class SendOrderEmail extends AsyncTask<String, Void, String>{
	@Override
	protected String doInBackground(String... param) {
        if (isCancelled()) return "Order Submission Cancelled. Please try again.";
        try
        {
        	GMailSender sender = new GMailSender("calljugnuapp@gmail.com","tanujchawla");
        	sender.sendMail("CallJugnu_App","CallJugnu App Order",param[0],"calljugnuapp@gmail.com","calljugnuapp@gmail.com");
            return "Order Placed Successfully.\nOur team will contact you soon.";
        }
        catch(Exception e)
        {
        	Log.e("error",e.getMessage(),e);
        	return "Order Not Submitted. Please try again.";
        }
	}

}
