 package byu.edu.isaacrh.familymapclient;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.EventResponse;
import Response.LoginResponse;
import Response.PersonResponse;
import Response.RegisterResponse;
import model.AuthToken;
import model.Event;
import model.Person;

 /**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public static final String HOST_KEY = "ReceivedHostKey";
    public static final String PORT_KEY = "ReceivedPortKey";
    private static final String SUCCESS_KEY = "SuccessKey";
    private static final String FULL_NAME_KEY = "FullNameKey";

    private Listener listener;

    public interface Listener {
        void notifyDone();
    }

    public void registerListener(Listener listener) {this.listener = listener;}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        EditText hostTextEdit = view.findViewById(R.id.serverHostString);
        EditText portTextEdit = view.findViewById(R.id.serverPortString);
        EditText usernameTextEdit = view.findViewById(R.id.userNameString);
        EditText passwordTextEdit = view.findViewById(R.id.passwordString);
        EditText firstNameTextEdit = view.findViewById(R.id.firstNameString);
        EditText lastNameTextEdit = view.findViewById(R.id.lastNameString);
        EditText emailTextEdit = view.findViewById(R.id.emailString);
        RadioButton maleRadioButton = view.findViewById(R.id.maleButton);
        RadioButton femaleRadioButton = view.findViewById(R.id.femaleButton);

        Button registerButton = view.findViewById(R.id.registerButton);
        Button loginButton = view.findViewById(R.id.loginButton);

        hostTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                        portTextEdit.getText().toString().trim().length() > 0 &&
                        usernameTextEdit.getText().toString().trim().length() > 0 &&
                        passwordTextEdit.getText().toString().trim().length() > 0);
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                        portTextEdit.getText().toString().trim().length() > 0 &&
                        usernameTextEdit.getText().toString().trim().length() > 0 &&
                        passwordTextEdit.getText().toString().trim().length() > 0 &&
                        firstNameTextEdit.getText().toString().trim().length() > 0 &&
                        lastNameTextEdit.getText().toString().trim().length() > 0 &&
                        emailTextEdit.getText().toString().trim().length() > 0);
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        portTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0);
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        usernameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0);
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0);
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        firstNameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lastNameTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registerButton.setEnabled(
                        hostTextEdit.getText().toString().trim().length() > 0 &&
                                portTextEdit.getText().toString().trim().length() > 0 &&
                                usernameTextEdit.getText().toString().trim().length() > 0 &&
                                passwordTextEdit.getText().toString().trim().length() > 0 &&
                                firstNameTextEdit.getText().toString().trim().length() > 0 &&
                                lastNameTextEdit.getText().toString().trim().length() > 0 &&
                                emailTextEdit.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordTextEdit.setText("password");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataCache.setServerHost(hostTextEdit.getText().toString());
                DataCache.setServerPort(portTextEdit.getText().toString());

                @SuppressLint("HandlerLeak") Handler uiThreadMessageHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        //this is executed in the UI thread -- how we write data back to the UI
                        Bundle bundle = message.getData();
                        boolean success = bundle.getBoolean(SUCCESS_KEY, false);
                        if(success) {
                            String fullName = bundle.getString(FULL_NAME_KEY, "NoName");

                            Toast.makeText(getActivity(), getString(R.string.firstNameResult, fullName),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.login_fail,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                };

                LoginRequest loginRequest = new LoginRequest(usernameTextEdit.getText().toString(),
                        passwordTextEdit.getText().toString());

                LoginTask loginTask = new LoginTask(uiThreadMessageHandler, loginRequest);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(loginTask);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataCache.setServerHost(hostTextEdit.getText().toString());
                DataCache.setServerPort(portTextEdit.getText().toString());

                @SuppressLint("HandlerLeak") Handler uiThreadMessageHandler = new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        Bundle bundle = message.getData();
                        boolean success = bundle.getBoolean(SUCCESS_KEY, false);
                        if(success) {
                            String fullName = bundle.getString(FULL_NAME_KEY, "NoName");

                            Toast.makeText(getActivity(), getString(R.string.firstNameResult, fullName),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.login_fail,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                String gender = "";
                if(maleRadioButton.isChecked()) {
                    gender = "m";
                }
                else if (femaleRadioButton.isChecked()) {
                    gender = "f";
                }

                RegisterRequest registerRequest = new RegisterRequest(usernameTextEdit.getText().toString(),
                        passwordTextEdit.getText().toString(), emailTextEdit.getText().toString(),
                        firstNameTextEdit.getText().toString(), lastNameTextEdit.getText().toString(),
                        gender);

                RegisterTask registerTask = new RegisterTask(uiThreadMessageHandler, registerRequest);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(registerTask);

            }
        });

        return view;
    }

    private static class LoginTask implements Runnable {

        private final Handler messageHandler;
        private final LoginRequest loginRequest;

        private LoginTask(Handler messageHandler, LoginRequest loginRequest) {
            this.messageHandler = messageHandler;
            this.loginRequest = loginRequest;
        }

        @Override
        public void run() {

            LoginResponse loginResponse = ServerProxy.login(loginRequest);
            Message message = Message.obtain();
            Bundle messageBundle = new Bundle();

            if(loginResponse == null || (!loginResponse.isSuccess())) {
                messageBundle.putBoolean(SUCCESS_KEY, false);
                message.setData(messageBundle);
                messageHandler.sendMessage(message);
            }
            else if (loginResponse.isSuccess()) {

                DataCache.getInstance();

                AuthToken authToken = new AuthToken(loginResponse.getAuthtoken(), loginResponse.getUsername());
                PersonResponse personResponse = ServerProxy.getPeopleForUser(authToken);
                EventResponse eventResponse = ServerProxy.getEventsForUser(authToken);

                assert personResponse != null;
                for(Person person : personResponse.getAncestors()) {
                    DataCache.getPeople().put(loginResponse.getPersonID(), person);
                }
                assert eventResponse != null;
                for(Event event : eventResponse.getData()) {
                    DataCache.getEvents().put(loginResponse.getPersonID(), event);
                }

                Person currUser = DataCache.getPersonById(loginResponse.getPersonID());

                String firstName = currUser.getFirstName();
                String lastName = currUser.getLastName();
                String fullName = firstName + " " + lastName;

                messageBundle.putString(FULL_NAME_KEY, fullName);
                messageBundle.putBoolean(SUCCESS_KEY, loginResponse.isSuccess());
                message.setData(messageBundle);

                messageHandler.sendMessage(message);

            }


        }
    }

    private static class RegisterTask implements Runnable {

        private final Handler messageHandler;
        private final RegisterRequest registerRequest;

        public RegisterTask(Handler messageHandler, RegisterRequest registerRequest) {
            this.messageHandler = messageHandler;
            this.registerRequest = registerRequest;
        }

        @Override
        public void run() {

            RegisterResponse registerResponse = ServerProxy.register(registerRequest);

            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            assert registerResponse != null;
            messageBundle.putBoolean(SUCCESS_KEY, registerResponse.isSuccess());
            message.setData(messageBundle);

            messageHandler.sendMessage(message);

        }
    }
}