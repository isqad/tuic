CREATE TABLE company_users (
  user_id integer not null,
  company_id integer not null
);

CREATE UNIQUE INDEX unique_index_company_users_on_user_id_and_company_id ON company_users (user_id, company_id);
CREATE INDEX index_company_users_on_company_id ON company_users (company_id);

ALTER TABLE company_users ADD CONSTRAINT fk_company_users_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE company_users ADD CONSTRAINT fk_company_users_companies FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE;
