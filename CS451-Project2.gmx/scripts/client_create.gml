///client_create()

RESULT = -1;

network_set_config(network_config_connect_timeout,5000);

SOCKET = network_create_socket(network_socket_tcp);
ISCONNECTED = network_connect_raw(SOCKET,"localhost",6790);

message = "";