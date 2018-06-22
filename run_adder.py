f = open('helloworld.txt','w')
#f.write('hello world')

f.write( """ "{runpar":{
        "run_number":0,"beam_energy":0.0,"current":0.0
        },
        "run_info":
        {""")

for i in range(2476,2478):     
    s_run = """"r""" + str(i) + """" """
    run = str(i)
    print s_run
    f.write(        
        s_run+""":
        {"run_number":"""+run+""",
        "beam_energy":6.4,
        "target":"LH2",
        "current":15.0,
        "cut_type":
        {
        "cut_nom":
        {
        "cutlvl":0,
        "cut_ecdetectors":
        {
        "ec_sf_cut":
        {
        "cut_name":"ECSFCut",
        "ec_max_fit_values_sector_1":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_1":[0.16132296185544553,0.08990047088178521,-0.1463522843721235],
        "ec_max_fit_values_sector_2":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_2":[0.16132296185544553,0.08990047088178521,-0.1463522843721235],
        "ec_max_fit_values_sector_3":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_3":[0.16132296185544553,0.08990047088178521,-0.1463522843721235],
        "ec_max_fit_values_sector_4":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_4":[0.16132296185544553,0.08990047088178521,-0.1463522843721235],
        "ec_max_fit_values_sector_5":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_5":[0.16132296185544553,0.08990047088178521,-0.1463522843721235],
        "ec_max_fit_values_sector_6":[0.340248532614159,-0.08337431099545847,0.056043017623713276],
        "ec_min_fit_values_sector_6":[0.16132296185544553,0.08990047088178521,-0.1463522843721235]
        }
        }        
        }
        }
        },"""
    )

f.close()
