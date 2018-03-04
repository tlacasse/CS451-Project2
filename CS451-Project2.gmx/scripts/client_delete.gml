///client_delete()

if (SOCKET >= 0){
    network_destroy(SOCKET);
}

ds_map_destroy(PEOPLE);
