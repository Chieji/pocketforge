# --- Stage 1: Build Stage ---
FROM rust:1.78-slim-bullseye AS builder

# Install dependencies for static linking and musl target
RUN apt-get update && apt-get install -y \
    pkg-config \
    libssl-dev \
    musl-tools \
    && rm -rf /var/lib/apt/lists/*

# Set the musl target
RUN rustup target add x86_64-unknown-linux-musl

WORKDIR /app

# Copy source code
COPY . .

# Build the release binary for musl target
RUN cargo build --release --target x86_64-unknown-linux-musl

# --- Stage 2: Final Stage ---
FROM scratch

# Install ca-certificates for HTTPS
COPY --from=builder /usr/share/ca-certificates /usr/share/ca-certificates
COPY --from=builder /etc/ssl/certs /etc/ssl/certs

# Copy the built binary
COPY --from=builder /app/target/x86_64-unknown-linux-musl/release/pocketforge_backend /usr/local/bin/pocketforge_backend

# Set environment variables
ENV RUST_LOG=info
ENV PORT=8080

# Expose the port
EXPOSE 8080

# Run the application
CMD ["/usr/local/bin/pocketforge_backend"]
