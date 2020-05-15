package com.smes.tinkerboard_gpio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ExecuteAsRootBase
{
	public static String testRoot()
	{
		String retval = "Success";
		Process suProcess;

		try
		{
			suProcess = Runtime.getRuntime().exec("su");

			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

			if (null != os && null != osRes)
			{
				// Getting the id of the current user to check if this is root
				os.writeBytes("id\n");
				os.flush();

				String currUid = osRes.readLine();
				boolean exitSu = false;
				if (null == currUid)
				{
					retval = "Can't get root access or denied by user";
					exitSu = false;
				}
				else if (true == currUid.contains("uid=0"))
				{
					exitSu = true;
					retval = "Root access granted";
				}
				else
				{
					exitSu = true;
					retval = "Root access rejected: " + currUid;
				}

				if (exitSu)
				{
					os.writeBytes("exit\n");
					os.flush();
				}
			}
		}
		catch (Exception e)
		{
			// Can't get root !
			// Probably broken pipe exception on trying to write to output stream (os) after su failed, meaning that the device is not rooted
			retval = "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage();
		}

		return retval;
	}

	public static boolean canRunRootCommands()
	{
		boolean retval = false;
		Process suProcess;

		try
		{
			suProcess = Runtime.getRuntime().exec("su");

			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

			if (null != os && null != osRes)
			{
				// Getting the id of the current user to check if this is root
				os.writeBytes("id\n");
				os.flush();

				String currUid = osRes.readLine();
				boolean exitSu = false;
				if (null == currUid)
				{
					retval = false;
					exitSu = false;
				}
				else if (true == currUid.contains("uid=0"))
				{
					retval = true;
					exitSu = true;
				}
				else
				{
					retval = false;
					exitSu = true;
				}

				if (exitSu)
				{
					os.writeBytes("exit\n");
					os.flush();
				}
			}
		}
		catch (Exception e)
		{
			// Can't get root !
			// Probably broken pipe exception on trying to write to output stream (os) after su failed, meaning that the device is not rooted

			retval = false;
		}

		return retval;
	}

	public final boolean execute(ArrayList<String> commands)
	{
		boolean retval = false;

		try
		{
			if (null != commands && commands.size() > 0)
			{
				Process suProcess = Runtime.getRuntime().exec("su");

				DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());


				// Execute commands that require root access
				for (String currCommand : commands)
				{
					os.writeBytes(currCommand + "\n");
					os.flush();
				}

				os.writeBytes("exit\n");
				os.flush();

				try
				{
					int suProcessRetval = suProcess.waitFor();
					if (255 != suProcessRetval)
					{
						// Root access granted
						retval = true;
					}
					else
					{
						// Root access denied
						retval = false;
					}
				}
				catch (Exception ex)
				{
				}
			}
		}
		catch (IOException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (Exception ex)
		{
		}

		return retval;
	}

	public final void execute(String command)
	{
		this.execute(command, false);
	}

	public final int execute(String command, boolean requiresOutput)
	{
		int retval = -1;

		try
		{

			Process suProcess = Runtime.getRuntime().exec("su");

			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

			// Execute command that requires root access
			os.writeBytes(command + "\n");
			os.flush();


			if(requiresOutput && osRes.available() > 0)
				retval = osRes.readInt();


			os.writeBytes("exit\n");
			os.flush();

			try
			{
				if(requiresOutput)
					return retval;

				int suProcessRetval = suProcess.waitFor();
				if (255 == suProcessRetval)
				{
					retval = -2;
				}
				else
				{
					return -10;
				}
			}
			catch (Exception ex)
			{
			}
		}
		catch (IOException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (Exception ex)
		{
		}

		return retval;
	}


	public static String executeWithReturn(String command)
	{
		String retval = "Success";
		Process suProcess;

		try
		{
			suProcess = Runtime.getRuntime().exec("su");

			DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
			DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

			if (null != os && null != osRes)
			{
				// Getting the id of the current user to check if this is root
				os.writeBytes(command + "\n");
				os.flush();

				retval = osRes.readLine();

				os.writeBytes("exit\n");
				os.flush();
			}
		}
		catch (Exception e)
		{
			// Can't get root !
			// Probably broken pipe exception on trying to write to output stream (os) after su failed, meaning that the device is not rooted
			retval = "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage();
		}

		return retval;
	}
}