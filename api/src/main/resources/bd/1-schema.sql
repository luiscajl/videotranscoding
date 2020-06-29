CREATE SCHEMA IF NOT EXISTS "videotranscoding";

CREATE TABLE if not EXISTS"videotranscoding"."original" (
  "id" serial PRIMARY KEY,
  "name" varchar(255) NOT NULL,
  "path" varchar(255) UNIQUE NOT NULL,
  "user" varchar(255)  NOT NULL,
"file_size" varchar(255) NOT NULL default '0',
  "complete" boolean NOT NULL DEFAULT false,
  "active" boolean NOT NULL DEFAULT false
);
CREATE TABLE if not exists "videotranscoding"."conversion" (
  "id" serial PRIMARY KEY,
  "original_id" int not null,
    "name" varchar(255) NOT NULL,
      "path" varchar(255) UNIQUE NOT NULL,
      "progress" varchar(255) UNIQUE NOT NULL,
  "finished" boolean NOT NULL DEFAULT false,
  "active" boolean NOT NULL DEFAULT false,
  "file_size" varchar(255) NOT NULL default '0',
"conversionType" varchar(255) NOT NULL
);

ALTER TABLE "videotranscoding"."conversion" ADD FOREIGN KEY ("original_id") REFERENCES "videotranscoding"."original" ("id");


