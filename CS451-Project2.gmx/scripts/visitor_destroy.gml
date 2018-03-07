///visitor_destroy()

if (ds_exists(gotox, ds_type_queue)){
    ds_queue_destroy(gotox);
    ds_queue_destroy(gotoy);
}
