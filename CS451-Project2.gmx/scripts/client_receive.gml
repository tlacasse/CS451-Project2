///client_receive()

// async called.
switch(ds_map_find_value(async_load, "type")){
    case network_type_data:
        var buffer = ds_map_find_value(async_load, "buffer");
        buffer_seek(buffer, buffer_seek_start, 0);
        process_buffer(buffer);
    break;
    //also exist network_type_connect & network_type_disconnect
}
