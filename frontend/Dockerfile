FROM nginx:1.18

COPY /dist/* /usr/share/nginx/html

COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

RUN echo "mainFileName=\"\$(ls /usr/share/nginx/html/main*.js)\" && \ 
          envsubst '\$BACKEND_API_URL \$OKTA_CLIENT_ID \$OKTA_ISSUER' < \${mainFileName} > main.tmp && \ 
          mv main.tmp \${mainFileName} && nginx -g 'daemon off;'" > run.sh