
import re

input_file = "basil.sql"
output_file = "clean_basil.sql"

# List of tables to strip data from (keep structure, remove INSERTs)
tables_to_clean = {
    "account",
    "clan", "clan_member", "clan_message", 
    "deposit", 
    "gift_code", "girlkun_gift", "girlkun_giftcode", 
    "history_gift", "history_gold", "history_receive_gold", "history_transaction", "trans_log", "tamkjll_history_code",
    "lucky_round", 
    "naptien", 
    "player", 
    "post_question", 
    "recharge_card", 
    "tamkjllgift", 
    "token", 
    "consignment_shop",
    "magic_tree", # Usually player specific
    "inventory", # If exists
    "item" # If exists separately
}

def clean_sql():
    print(f"Reading {input_file}...")
    with open(input_file, 'r', encoding='utf-8', errors='ignore') as f_in, \
         open(output_file, 'w', encoding='utf-8') as f_out:
        
        skip_mode = False
        skipped_table = ""
        
        for line in f_in:
            # Check for start of INSERT statement
            insert_match = re.match(r"^INSERT INTO `([^`]+)`", line)
            
            if insert_match:
                table_name = insert_match.group(1)
                if table_name in tables_to_clean:
                    skip_mode = True
                    skipped_table = table_name
                    # Check if the statement ends on the same line
                    if ";" in line:
                        skip_mode = False
                        f_out.write(f"-- Data for table `{table_name}` skipped by cleaner\n")
                    continue
            
            if skip_mode:
                if ";" in line:
                    skip_mode = False
                    f_out.write(f"-- Data for table `{skipped_table}` skipped by cleaner\n")
                continue
            
            # Write line if not skipping
            f_out.write(line)

    print(f"Finished! Clean file saved to {output_file}")

if __name__ == "__main__":
    clean_sql()
