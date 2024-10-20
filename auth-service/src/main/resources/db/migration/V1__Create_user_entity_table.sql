-- Create the user_entity table
CREATE TABLE IF NOT EXISTS public.user_entity (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username character varying(255) NOT NULL UNIQUE,
    first_name character varying(255),
    last_name character varying(255),
    email character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL,
    account_non_expired boolean NOT NULL DEFAULT true,
    account_non_locked boolean NOT NULL DEFAULT true,
    credentials_non_expired boolean NOT NULL DEFAULT true,
    enabled boolean NOT NULL DEFAULT true,
    version bigint NOT NULL,
    created_at timestamp(6) without time zone DEFAULT now(),
    updated_at timestamp(6) without time zone DEFAULT now()
);

-- Create indexes on username and email for faster lookups
CREATE INDEX idx_user_entity_username ON public.user_entity(username);
CREATE INDEX idx_user_entity_email ON public.user_entity(email);
